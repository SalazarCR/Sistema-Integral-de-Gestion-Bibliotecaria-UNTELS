package pe.edu.untels.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.StudentDTO;
import pe.edu.untels.dtos.StudentRegisterDTO;
import pe.edu.untels.servicesinterfaces.IStudentService;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private IStudentService studentService;

    @PostMapping("/registro")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterDTO studentRegisterDTO) {
        try {
            log.info(">>> [ESTUDIANTE] Registrando estudiante con código: {}", studentRegisterDTO.getCodigoStudent());
            StudentDTO response = studentService.registerStudent(studentRegisterDTO);
            return ResponseEntity.status(201).body(createResponse(true, "Estudiante registrado exitosamente", response, 201));
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error al registrar: {}", e.getMessage());
            return ResponseEntity.status(400).body(createErrorResponse("Error al registrar estudiante", e.getMessage(), 400));
        }
    }

    @GetMapping("/lista")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<StudentDTO>> listStudents() {
        try {
            log.info(">>> [ESTUDIANTE] Listando todos los estudiantes");
            List<StudentDTO> students = studentService.listStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error al listar: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/lista-activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<StudentDTO>> listActiveStudents() {
        try {
            log.info(">>> [ESTUDIANTE] Listando estudiantes activos");
            List<StudentDTO> students = studentService.listActiveStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error al listar activos: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{idStudent}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> getStudent(@PathVariable int idStudent) {
        try {
            log.info(">>> [ESTUDIANTE] Obteniendo estudiante ID: {}", idStudent);
            var studentOpt = studentService.getStudentById(idStudent);
            if (studentOpt.isPresent()) {
                return ResponseEntity.ok(studentOpt.get());
            }
            return ResponseEntity.status(404).body(createErrorResponse("Estudiante no encontrado", "ID: " + idStudent, 404));
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error al obtener estudiante", e.getMessage(), 500));
        }
    }

    @GetMapping("/codigo/{codigoStudent}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> getStudentByCodigo(@PathVariable String codigoStudent) {
        try {
            log.info(">>> [ESTUDIANTE] Obteniendo estudiante por código: {}", codigoStudent);
            var studentOpt = studentService.getStudentByCodigo(codigoStudent);
            if (studentOpt.isPresent()) {
                return ResponseEntity.ok(studentOpt.get());
            }
            return ResponseEntity.status(404).body(createErrorResponse("Estudiante no encontrado", "Código: " + codigoStudent, 404));
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error al obtener estudiante", e.getMessage(), 500));
        }
    }

    @PutMapping("/{idStudent}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStudent(@PathVariable int idStudent, @RequestBody StudentDTO studentDTO) {
        try {
            log.info(">>> [ESTUDIANTE] Actualizando estudiante ID: {}", idStudent);
            StudentDTO updatedStudent = studentService.updateStudent(idStudent, studentDTO);
            return ResponseEntity.ok(createResponse(true, "Estudiante actualizado exitosamente", updatedStudent, 200));
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error al actualizar: {}", e.getMessage());
            return ResponseEntity.status(400).body(createErrorResponse("Error al actualizar estudiante", e.getMessage(), 400));
        }
    }

    @DeleteMapping("/{idStudent}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable int idStudent) {
        try {
            log.info(">>> [ESTUDIANTE] Eliminando estudiante ID: {}", idStudent);
            studentService.deleteStudent(idStudent);
            return ResponseEntity.ok(createResponse(true, "Estudiante eliminado correctamente", null, 200));
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error al eliminar: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error al eliminar estudiante", e.getMessage(), 500));
        }
    }

    @GetMapping("/buscar/nombre")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> searchStudentByName(@RequestParam String nombre) {
        try {
            log.info(">>> [ESTUDIANTE] Buscando estudiantes por nombre: {}", nombre);
            var students = studentService.searchStudentByName(nombre);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error en búsqueda: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error en búsqueda", e.getMessage(), 500));
        }
    }

    @GetMapping("/filtrar/carrera/{idCarrera}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> filterStudentByCarrera(@PathVariable int idCarrera) {
        try {
            log.info(">>> [ESTUDIANTE] Filtrando estudiantes por carrera ID: {}", idCarrera);
            var students = studentService.filterStudentByCarrera(idCarrera);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error en filtro: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error en filtro", e.getMessage(), 500));
        }
    }

    @GetMapping("/filtrar/estado/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> filterStudentByStatus(@PathVariable boolean status) {
        try {
            log.info(">>> [ESTUDIANTE] Filtrando estudiantes por estado: {}", status);
            var students = studentService.filterStudentByStatus(status);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            log.error(">>> [ESTUDIANTE] ❌ Error en filtro: {}", e.getMessage());
            return ResponseEntity.status(500).body(createErrorResponse("Error en filtro", e.getMessage(), 500));
        }
    }

    private Map<String, Object> createResponse(boolean success, String message, Object data, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", message);
        response.put("data", data);
        response.put("status", status);
        return response;
    }

    private Map<String, Object> createErrorResponse(String message, String error, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("error", error);
        response.put("status", status);
        return response;
    }
}

