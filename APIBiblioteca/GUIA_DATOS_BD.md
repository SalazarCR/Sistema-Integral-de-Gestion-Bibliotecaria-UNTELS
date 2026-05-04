# 📊 GUÍA: DATOS EN LA BASE DE DATOS

---

## ❓ ¿DÓNDE ESTÁN LOS USUARIOS?

La base de datos **bdbiblioteca** está **VACÍA** porque:

```
spring.jpa.hibernate.ddl-auto=create
        ↓
Cada vez que inicia el app, RECREA las tablas
        ↓
PIERDE todos los datos anteriores
```

---

## ✅ SOLUCIÓN APLICADA

Cambié la configuración a:
```properties
spring.jpa.hibernate.ddl-auto=update
```

Ahora:
- ✅ Las tablas se crean la primera vez
- ✅ Los datos se PRESERVAN en siguientes inicios
- ✅ Solo se agregan columnas nuevas si se necesitan

---

## 🔧 CÓMO INSERTAR DATOS INICIALES

### Opción 1: Usar pgAdmin (Visual)

#### Paso 1: Abrir pgAdmin
```
1. Abre pgAdmin (http://localhost:5050)
2. Usuario: postgres
3. Contraseña: [la que usaste en instalación]
4. Conecta al servidor local
```

#### Paso 2: Ejecutar Script SQL
```
1. Click derecho en "bdbiblioteca" → Query Tool
2. Abre archivo: init_data.sql
3. Copia y pega TODO el contenido
4. Presiona F5 o "Execute" (▶)
5. ✓ Verás mensajes de inserción
```

#### Resultado en pgAdmin:
```
Verás las tablas:
├─ "Role" (3 registros)
├─ "User" (5 registros) ← AQUÍ ESTÁN LOS USUARIOS
├─ "Carrera" (4 registros)
├─ "Student" (3 registros)
└─ [otras tablas]
```

---

### Opción 2: Desde línea de comandos

```bash
# Si tienes psql instalado en tu PATH:
psql -U postgres -d bdbiblioteca -f init_data.sql

# Si NO está en PATH, usa la ruta completa:
"C:\Program Files\PostgreSQL\16\bin\psql" -U postgres -d bdbiblioteca -f init_data.sql
```

---

## 👥 USUARIOS QUE SE CREARÁN

### 1. **Admin**
```
Username: admin
Password: admin123
Email: admin@untels.edu.pe
Status: Activo ✅
Rol: Admin
```

### 2. **Bibliotecario**
```
Username: bibliotecario
Password: admin123
Email: biblio@untels.edu.pe
Status: Activo ✅
Rol: Bibliotecario
```

### 3-5. **Estudiantes**
```
Usuario 1: estudiante1 | Status: Activo ✅
Usuario 2: estudiante2 | Status: Activo ✅
Usuario 3: estudiante3 | Status: Bloqueado ❌
```

---

## 🔧 VERIFICAR DATOS EN LA BD

### En pgAdmin:

#### Ver todos los usuarios:
```sql
SELECT id_user, username_user, email_user, status_user 
FROM "User" 
ORDER BY id_user;
```

#### Ver estudiantes:
```sql
SELECT id_student, codigo_student, name_student, status_student 
FROM "Student" 
ORDER BY id_student;
```

#### Ver roles:
```sql
SELECT * FROM "Role";
```

---

## 🧪 PROBAR CON LOS ENDPOINTS

### 1. Login con admin:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameUser":"admin","passwordUser":"admin123"}'
```

**Respuesta esperada:**
```json
{
  "idUser": 1,
  "usernameUser": "admin",
  "emailUser": "admin@untels.edu.pe",
  "nameRole": "Admin",
  "statusUser": true
}
```

### 2. Ver estado de un usuario:
```bash
curl -X GET http://localhost:8080/api/auth/status/1
```

### 3. Toggle (activar/desactivar):
```bash
curl -X PUT http://localhost:8080/api/auth/toggle/3
```

### 4. Ver estudiantes:
```bash
curl -X GET http://localhost:8080/api/estudiantes/lista
```

---

## 📋 ESTRUCTURA DE TABLAS

### Tabla: "User"
```
id_user          | INT (PK)
username_user    | VARCHAR
password_user    | VARCHAR (encriptada con BCrypt)
email_user       | VARCHAR
status_user      | BOOLEAN (true=activo, false=bloqueado)
date_register_user | TIMESTAMP
id_role          | INT (FK)
```

### Tabla: "Role"
```
id_role         | INT (PK)
name_role       | VARCHAR (Admin, Bibliotecario, Estudiante)
```

### Tabla: "Student"
```
id_student              | INT (PK)
codigo_student          | VARCHAR
name_student            | VARCHAR
email_student           | VARCHAR
phone_student           | VARCHAR
status_student          | BOOLEAN
library_access_student  | BOOLEAN
date_register_student   | DATE
id_carrera              | INT (FK)
id_user                 | INT (FK OneToOne)
```

### Tabla: "Carrera"
```
id_carrera      | INT (PK)
name_carrera    | VARCHAR
```

---

## 🔄 OPCIONES DE INSERCIÓN

### Para crear usuarios vía API (después de insertar datos iniciales):

```bash
curl -X POST http://localhost:8080/api/estudiantes/registro \
  -H "Content-Type: application/json" \
  -d '{
    "codigoStudent": "EST2024004",
    "nameStudent": "Nuevo Estudiante",
    "emailStudent": "nuevo@untels.edu.pe",
    "phoneStudent": "999999999",
    "idCarrera": 1,
    "usernameUser": "nuevo.usuario",
    "passwordUser": "password123"
  }'
```

---

## 📊 RESUMEN DEL ESTADO

| Componente | Estado | Acción |
|-----------|--------|--------|
| BD PostgreSQL | ¿Activa? | Verificar en pgAdmin |
| Tabla "User" | Vacía | Ejecutar init_data.sql |
| Tabla "Carrera" | Vacía | Ejecutar init_data.sql |
| Tabla "Student" | Vacía | Ejecutar init_data.sql |
| Configuración | ✅ Actualizada | ddl-auto=update |
| Endpoints | ✅ Funcionales | Listos para usar |

---

## ✅ PASOS RÁPIDOS

```
1. Abre pgAdmin (http://localhost:5050)
2. Click en "bdbiblioteca" → Query Tool
3. Copia todo el contenido de init_data.sql
4. Pega y ejecuta (F5)
5. ✓ Verás los datos insertados
6. Prueba los endpoints
7. ✓ Listo!
```

---

**Notas importantes:**
- Los datos se preservarán ahora con ddl-auto=update
- Las contraseñas están encriptadas con BCrypt
- Todos los IDs están referenciados correctamente
- Las tablas tienen restricciones de integridad referencial

¿Necesitas ayuda con pgAdmin?

