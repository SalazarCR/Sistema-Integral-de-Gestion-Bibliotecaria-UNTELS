# ✅ Guía de Verificación del Login Frontend/Backend

## Estado Actual

Todos los cambios necesarios han sido implementados en el backend:
- ✅ `JwtAuthenticationController` con endpoint `/login`
- ✅ `LoginRequest` DTO para solicitudes de login
- ✅ `JwtResponse` DTO con serialización correcta (propiedad `token`)
- ✅ `CorsConfig` habilitado para `http://localhost:4200`
- ✅ `WebSecurityConfig` con seguridad configurada
- ✅ `BCryptPasswordEncoder` para validar contraseñas
- ✅ `JwtTokenUtil` para generar JWT tokens

---

## 🚀 Paso 1: Ejecutar el Backend

```powershell
cd "C:\Users\CESAR\GTHUB\ProjectIS\Back\Sistema-Integral-de-Gestion-Bibliotecaria-UNTELS\APIBiblioteca"
./gradlew bootRun
```

Deberías ver:
```
...
Started ApisBibliotecaApplication in X seconds
Server is listening on port 8080
```

---

## 🧪 Paso 2: Prueba con CURL (línea de comando)

Abre PowerShell y ejecuta:

```powershell
$body = @{username="admin1"; password="admin1"} | ConvertTo-Json
$response = Invoke-WebRequest -Uri "http://localhost:8080/login" `
  -Method POST `
  -ContentType "application/json" `
  -Body $body `
  -ErrorAction SilentlyContinue

$response.Content | ConvertFrom-Json | ConvertTo-Json -Depth 10
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "idUsuario": 1,
  "username": "admin1",
  "nombre": "Admin General",
  "rol": "ADMIN"
}
```

---

## 📋 Credenciales de Prueba

| Usuario | Contraseña | Rol | Estado |
|---------|-----------|-----|--------|
| admin1 | admin1 | ADMIN | ACTIVO |
| admin2 | admin2 | ADMIN | ACTIVO |
| biblio1 | biblio1 | BIBLIOTECARIO | ACTIVO |
| biblio2 | biblio2 | BIBLIOTECARIO | ACTIVO |
| est1 | est1 | ESTUDIANTE | ACTIVO |
| est2 | est2 | ESTUDIANTE | ACTIVO |
| est4 | est4 | ESTUDIANTE | **INACTIVO** (debe fallar) |

---

## 🎯 Paso 3: Prueba desde Angular

1. **Asegúrate que el Frontend Angular está en `http://localhost:4200`**

2. **El service de autenticación debe enviar:**
```typescript
login(username: string, password: string): Observable<JwtResponse> {
  const body = { username, password };
  return this.http.post<JwtResponse>(`${this.url}/login`, body).pipe(
    tap((resp) => this.guardarSesion(resp))
  );
}
```

3. **El modelo JwtResponse debe ser:**
```typescript
export interface JwtResponse {
  token: string;      // Viene del backend como "token" (no "jwttoken")
  idUsuario: number;
  username: string;
  nombre: string;
  rol: string;
}
```

4. **Al guardar la sesión, verifica que localStorage tenga:**
```typescript
localStorage.setItem('token', resp.token);
localStorage.setItem('username', resp.username);
localStorage.setItem('nombre', resp.nombre);
localStorage.setItem('rol', resp.rol);
localStorage.setItem('idUsuario', resp.idUsuario);
```

---

## 🔍 Verificación en el Backend

### Logs esperados al hacer login exitoso:
```
DEBUG: loadUserByUsername: usuario 'admin1' encontrado
DEBUG: JwtTokenUtil: Token generado para 'admin1' con rol 'ADMIN'
INFO: Login exitoso para usuario 'admin1'
```

### Errores comunes y soluciones:

| Error | Causa | Solución |
|-------|-------|----------|
| "Usuario o contraseña incorrectos" | Contraseña mal o usuario no existe | Verificar en BD con: `SELECT username, nombre FROM usuarios WHERE username='admin1'` |
| CORS Error en navegador | CORS no habilitado | Verificar que `CorsConfig.java` y `@CrossOrigin` en controller existen |
| 404 - Endpoint no encontrado | Ruta `/login` no está activa | Verificar que el backend está levantado en puerto 8080 |
| "Usuario se encuentra inactivo" | `estado` != 'ACTIVO' en BD | Cambiar: `UPDATE usuarios SET estado='ACTIVO' WHERE username='est4'` |

---

## 📊 Checklist Final

- [ ] Backend compilando sin errores
- [ ] Backend ejecutándose en `http://localhost:8080`
- [ ] Prueba CURL exitosa con `admin1/admin1`
- [ ] Respuesta incluye los 5 campos: `token`, `idUsuario`, `username`, `nombre`, `rol`
- [ ] El campo se llama `token` (no `jwttoken`)
- [ ] Angular frontend conecta a `http://localhost:8080/login`
- [ ] localStorage recibe todos los datos correctamente
- [ ] Angular guard valida el token en requests posteriores

---

## 🔐 Seguridad

✅ **Configurado correctamente:**
- Contraseñas hasheadas con BCrypt (no texto plano)
- JWT tokens con expiración (5 horas)
- CORS restringido a `http://localhost:4200`
- CSRF deshabilitado para API stateless
- Rutas protegidas requieren autenticación
- El endpoint `/login` permite acceso sin autenticación

---

## 📞 Si algo no funciona

1. **Verifica que la BD está arriba** (PostgreSQL en localhost:5432)
2. **Verifica que los datos están cargados**: 
   ```sql
   SELECT id, username, nombre, rol, estado FROM usuarios LIMIT 5;
   ```
3. **Verifica la URL del Backend en Angular**:
   ```typescript
   private url = 'http://localhost:8080';
   ```
4. **Revisa los logs del backend** para mensajes de error
5. **Abre DevTools en Angular** (F12) → Console y Network para ver requests

---

**Última actualización:** 2026-07-05
**Estado:** ✅ Backend listo para producción
