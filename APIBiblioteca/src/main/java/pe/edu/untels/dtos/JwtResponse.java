package pe.edu.untels.dtos;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;
    private final int idUsuario;
    private final String username;
    private final String nombre;
    private final String rol;

    public JwtResponse(String jwttoken, int idUsuario, String username, String nombre, String rol) {
        this.jwttoken = jwttoken;
        this.idUsuario = idUsuario;
        this.username = username;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }
}
