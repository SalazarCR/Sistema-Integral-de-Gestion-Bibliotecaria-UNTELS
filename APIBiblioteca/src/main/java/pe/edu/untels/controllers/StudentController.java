package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.StudentDTO;
import pe.edu.untels.dtos.StudentRegisterDTO;
import pe.edu.untels.servicesinterfaces.IStudentService;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @PostMapping("/registro")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterDTO studentRegisterDTO) {
        try {
            StudentDTO response = studentService.registerStudent(studentRegisterDTO);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<StudentDTO>> listStudents() {
        try {
            List<StudentDTO> students = studentService.listStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/lista-activos")
    public ResponseEntity<List<StudentDTO>> listActiveStudents() {
        try {
            List<StudentDTO> students = studentService.listActiveStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{idStudent}")
    public ResponseEntity<?> getStudent(@PathVariable int idStudent) {
        try {
            var studentOpt = studentService.getStudentById(idStudent);
            if (studentOpt.isPresent()) {
                return ResponseEntity.ok(studentOpt.get());
            }
            return ResponseEntity.status(404).body("Estudiante no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/codigo/{codigoStudent}")
    public ResponseEntity<?> getStudentByCodigo(@PathVariable String codigoStudent) {
        try {
            var studentOpt = studentService.getStudentByCodigo(codigoStudent);
            if (studentOpt.isPresent()) {
                return ResponseEntity.ok(studentOpt.get());
            }
            return ResponseEntity.status(404).body("Estudiante no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{idStudent}")
    public ResponseEntity<?> updateStudent(@PathVariable int idStudent, @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO updatedStudent = studentService.updateStudent(idStudent, studentDTO);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idStudent}")
    public ResponseEntity<?> deleteStudent(@PathVariable int idStudent) {
        try {
            studentService.deleteStudent(idStudent);
            return ResponseEntity.ok("Estudiante eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<?> searchStudentByName(@RequestParam String nombre) {
        try {
            var students = studentService.searchStudentByName(nombre);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/filtrar/carrera/{idCarrera}")
    public ResponseEntity<?> filterStudentByCarrera(@PathVariable int idCarrera) {
        try {
            var students = studentService.filterStudentByCarrera(idCarrera);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/filtrar/estado/{status}")
    public ResponseEntity<?> filterStudentByStatus(@PathVariable boolean status) {
        try {
            var students = studentService.filterStudentByStatus(status);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

