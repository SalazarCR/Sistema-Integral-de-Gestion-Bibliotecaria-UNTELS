# 📊 GUÍA VISUAL: CÓMO FUNCIONA MERGE Y PUSH EN GIT

---

## 🔄 PASO 1: ENTENDER LAS RAMAS LOCALES VS REMOTAS

```
LOCAL (Tu máquina):
  main  ←─ HEAD (apunta aquí)
  ├─ commit 7c75a32  ← Nuevo (del merge)
  ├─ commit 4327ee3  ← Nuevo (del merge)
  └─ commit 862d8ad  ← Era main antes

REMOTO (GitHub):
  origin/main  ←─ Apunta aquí en el servidor
  └─ commit 862d8ad  ← No ha cambiado

TÚ ESTÁS 2 COMMITS ADELANTE
```

---

## 🎯 PASO 2: VER DIFERENCIAS ANTES DE MERGE

### Comando para ver qué va a cambiar:
```bash
git diff main feature/HUF14-toggle-usuarios --stat
```

### Resultado:
```
 AuthController.java              | 45 ++++---
 StatusResponseDTO.java           | 38 ++++++
 ToggleResponseDTO.java           | 38 ++++++
 UserServiceImplement.java        | 11 ++++
 IUserService.java                |  1 +
 
5 files changed, 113 insertions(+), 20 deletions(-)
```

---

## ✅ PASO 3: VER COMMITS QUE SERÁN MERGEADOS

### Comando:
```bash
git log --oneline main..feature/HUF14-toggle-usuarios
```

### Resultado:
```
7c75a32  HUF14-HUF15: Crear endpoints para toggle
4327ee3  HUF14: Implementar método de toggle
```

---

## 🔀 PASO 4: HACER EL MERGE

### Comando:
```bash
git merge feature/HUF14-toggle-usuarios
```

### Resultado:
```
Updating 862d8ad..7c75a32
Fast-forward
 file1.java | 45 +++
 file2.java | 38 +++
 file3.java | 38 +++

✅ MERGE COMPLETADO
```

---

## 📈 PASO 5: VISUALIZAR EL RESULTADO

### Árbol de commits ANTES del merge:
```
        feature/HUF14
             ↓
    7c75a32 ─ Endpoint toggle
    4327ee3 ─ Método toggle
    ↓
862d8ad ─ main (origin/main)
```

### Árbol de commits DESPUÉS del merge:
```
    7c75a32 ─ Endpoint toggle  ← main ahora señala aquí
    4327ee3 ─ Método toggle
    ↓
862d8ad ─ origin/main (aún aquí en remoto)
```

---

## 📤 PASO 6: HACER PUSH AL REPOSITORIO REMOTO

### Estado ANTES de push:
```
LOCAL:            REMOTO (GitHub):
main ← 7c75a32    origin/main ← 862d8ad
      ↓
    4327ee3       (Sin cambios)
      ↓
    862d8ad
```

### Comando:
```bash
git push origin main
```

### Resultado:
```
Enumerating objects: 5, done.
Counting objects: 100% (5/5), done.
Delta compression using up to 8 threads
Compressing objects: 100% (3/3), done.
Writing objects: 100% (2/2), 1.2 KiB, done.

7c75a32..862d8ad  main → main
✅ PUSH COMPLETADO
```

### Estado DESPUÉS de push:
```
LOCAL:            REMOTO (GitHub):
main ← 7c75a32    origin/main ← 7c75a32
      ↓                   ↓
    4327ee3            4327ee3
      ↓                   ↓
    862d8ad            862d8ad

✅ SINCRONIZADAS
```

---

## 🖥️ CÓMO SE VE EN GITHUB

### En la pestaña "Code":
```
📁 commits
  └─ main
     └─ 7c75a32 HUF14-HUF15: Crear endpoints para toggle
     └─ 4327ee3 HUF14: Implementar método de toggle
     └─ 862d8ad Sprint 2 - Tarea 2...
```

### En la pestaña "Network" (Gráfico de commits):
```
main ─── 7c75a32 ─── 4327ee3 ─── 862d8ad
  ↑
  └─ HEAD apunta aquí localmente

origin/main ─── 862d8ad
  ↑
  └─ En el servidor
```

### En la pestaña "Branches":
```
Branch              Last commit              Status
─────────────────────────────────────────────────────
main                7c75a32 (2 commits)      Up to date
feature/HUF14-...   7c75a32 (2 commits)      Merged ✓
```

---

## 🔍 VERIFICAR ESTADO EN CUALQUIER MOMENTO

```bash
# Ver si hay cambios sin hacer push
git status

# Ver diferencias con remoto
git log --oneline -5
git log --oneline origin/main -5

# Ver estado visual
git log --all --graph --oneline -10
```

---

## 📋 RESUMEN: FLUJO COMPLETO

```
1️⃣  Desarrollo en rama      feature/HUF14-toggle-usuarios
                            ├─ Commit 1
                            └─ Commit 2

2️⃣  Cambiar a main         git checkout main

3️⃣  Ver cambios             git diff main..feature/HUF14

4️⃣  Hacer merge             git merge feature/HUF14-toggle-usuarios
                            ✓ Cambios ahora en main local

5️⃣  Hacer push              git push origin main
                            ✓ Cambios ahora en GitHub

6️⃣  Verificar en GitHub     • Ver commits en Code
                            • Ver gráfico en Network
                            • Ver rama mergeada en Branches
```

---

## ⚙️ ARCHIVOS ACTUALIZADOS EN GITHUB DESPUÉS DE PUSH

```
Repositorio: Sistema-Integral-de-Gestion-Bibliotecaria-UNTELS
    └─ APIBiblioteca
       ├─ src/main/java/pe/edu/untels/controllers/
       │  └─ AuthController.java ✨ MODIFICADO
       ├─ src/main/java/pe/edu/untels/dtos/
       │  ├─ StatusResponseDTO.java ✨ NUEVO
       │  ├─ ToggleResponseDTO.java ✨ NUEVO
       │  └─ [otros DTOs]
       └─ src/main/java/pe/edu/untels/servicesimplements/
          ├─ UserServiceImplement.java ✨ MODIFICADO
          └─ [otros servicios]
```

---

## 🎯 ESTADO ACTUAL

```
Local Status:
  ✓ Repository: Up to date with origin
  ✓ Branch: main
  ✓ Working directory: clean
  ✓ Commits ahead of origin/main: 2

Cambios listos para push:
  • HUF14-HUF15: Crear endpoints para toggle
  • HUF14: Implementar método de toggle
```

---

## 📲 PRÓXIMO PASO: HACER PUSH

```bash
git push origin main
```

Esto hará que:
1. ✅ Los 2 commits se suban a GitHub
2. ✅ La rama main en GitHub se actualice
3. ✅ Los cambios sean visibles para otros desarrolladores
4. ✅ El repositorio remoto se sincronice con el local

---

## 🔗 REFERENCIAS ÚTILES

- Ver cambios: `git diff RAMA1 RAMA2`
- Ver commits: `git log --oneline RAMA1..RAMA2`
- Hacer merge: `git merge RAMA_ORIGIN`
- Hacer push: `git push origin RAMA_LOCAL`
- Ver estado: `git status`
- Ver historial: `git log --graph --all --oneline`

---

**Creado:** 2026-05-03  
**Estado:** Merge completado, listo para push  
**Responsable:** Cesar Salazar

