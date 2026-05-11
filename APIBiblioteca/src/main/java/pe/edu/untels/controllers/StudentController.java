package pe.edu.untels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.dtos.StudentDTO;
import pe.edu.untels.entities.Student;
import pe.edu.untels.servicesinterfaces.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student registrar(@RequestBody StudentDTO dto) {
        return studentService.registrar(dto);
    }

    @GetMapping("/estado/{estado}")
    public List<Student> filtrarPorEstado(@PathVariable Boolean estado) {
        return studentService.filtrarPorEstado(estado);
    }
    @GetMapping
    public Page<Student> listar(Pageable pageable) {
        return studentService.listar(pageable);
    }

    @PutMapping("/{id}")
    public Student actualizar(@PathVariable Long id,
                              @RequestBody StudentDTO dto) {
        return studentService.actualizar(id, dto);
    }

    @PatchMapping("/{id}/toggle")
    public Student toggleEstado(@PathVariable Long id) {
        return studentService.toggleEstado(id);
    }

    @GetMapping("/dni/{dni}")
    public Student buscarPorDni(@PathVariable String dni) {
        return studentService.buscarPorDni(dni);
    }

    @GetMapping("/codigo/{codigo}")
    public Student buscarPorCodigo(@PathVariable String codigo) {
        return studentService.buscarPorCodigo(codigo);
    }

    @GetMapping("/carrera/{id}")
    public List<Student> filtrarPorCarrera(@PathVariable Long id) {
        return studentService.filtrarPorCarrera(id);
    }
}
