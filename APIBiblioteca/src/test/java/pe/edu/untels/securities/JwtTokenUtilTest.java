package pe.edu.untels.securities;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias puras de JwtTokenUtil (sin contexto de Spring).
 *
 * El campo "secret" se inyecta normalmente via @Value, asi que en un test sin
 * Spring hay que asignarlo por reflexion antes de generar/validar tokens.
 */
class JwtTokenUtilTest {

    private static final String TEST_SECRET = "UNTELS2026SecretKeyForJWTTokenBiblioBibliotecaAPISpringBootTest";

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() throws Exception {
        jwtTokenUtil = new JwtTokenUtil();
        Field secretField = JwtTokenUtil.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtTokenUtil, TEST_SECRET);
    }

    @Test
    void generateToken_generaUnTokenNoNuloConTresPartes() {
        String token = jwtTokenUtil.generateToken("jperez");

        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3, "un JWT debe tener 3 segmentos separados por punto");
    }

    @Test
    void getUsernameFromToken_devuelveElUsernameUsadoParaGenerarlo() {
        String token = jwtTokenUtil.generateToken("jperez");

        assertTrue(jwtTokenUtil.getUsernameFromToken(token).equals("jperez"));
    }

    @Test
    void getExpirationDateFromToken_esPosteriorAAhora() {
        String token = jwtTokenUtil.generateToken("jperez");

        Date expiracion = jwtTokenUtil.getExpirationDateFromToken(token);

        assertTrue(expiracion.after(new Date()));
    }

    @Test
    void validateToken_true_cuandoUsernameCoincideYNoExpiro() {
        String token = jwtTokenUtil.generateToken("jperez");

        assertTrue(jwtTokenUtil.validateToken(token, "jperez"));
    }

    @Test
    void validateToken_false_cuandoElUsernameNoCoincide() {
        String token = jwtTokenUtil.generateToken("jperez");

        assertFalse(jwtTokenUtil.validateToken(token, "otroUsuario"));
    }

    @Test
    void validateToken_lanzaExcepcion_cuandoElTokenYaExpiro() {
        SecretKey key = Keys.hmacShaKeyFor(TEST_SECRET.getBytes(StandardCharsets.UTF_8));
        String tokenExpirado = Jwts.builder()
                .subject("jperez")
                .issuedAt(new Date(System.currentTimeMillis() - 10_000))
                .expiration(new Date(System.currentTimeMillis() - 5_000))
                .signWith(key)
                .compact();

        assertThrows(ExpiredJwtException.class, () -> jwtTokenUtil.validateToken(tokenExpirado, "jperez"));
    }

    @Test
    void getUsernameFromToken_lanzaExcepcion_cuandoElTokenFueFirmadoConOtraClave() {
        SecretKey otraClave = Keys.hmacShaKeyFor(
                "OtraClaveTotalmenteDistintaQueNoCoincideConLaConfigurada12345".getBytes(StandardCharsets.UTF_8));
        String tokenConOtraFirma = Jwts.builder()
                .subject("jperez")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(otraClave)
                .compact();

        assertThrows(io.jsonwebtoken.security.SignatureException.class,
                () -> jwtTokenUtil.getUsernameFromToken(tokenConOtraFirma));
    }
}
