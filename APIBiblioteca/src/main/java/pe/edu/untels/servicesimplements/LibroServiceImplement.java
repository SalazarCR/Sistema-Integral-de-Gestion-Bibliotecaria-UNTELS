package pe.edu.untels.servicesimplements;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.edu.untels.dtos.LibroApiExternaDTO;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.repositories.ILibroRepository;
import pe.edu.untels.servicesinterfaces.ILibroService;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImplement implements ILibroService {

    @Autowired
    private ILibroRepository libroRepository;

    @Override
    public List<Libro> list() {
        return libroRepository.findAll();
    }

    @Override
    public Libro insert(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public Optional<Libro> listId(int id) {
        return libroRepository.findById(id);
    }

    @Override
    public void edit(Libro libro) {
        libroRepository.save(libro);
    }

    @Override
    public void delete(int id) {
        libroRepository.deleteById(id);
    }

    @Override
    public List<Libro> buscarPorCategoria(String categoria) {
        return libroRepository.findByCategoria(categoria);
    }

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public boolean existeIsbn(String isbn) {
        return libroRepository.existsByIsbn(isbn);
    }

    @Override
    public LibroApiExternaDTO buscarPorIsbnEnApi(String isbn) {
        LibroApiExternaDTO dto = new LibroApiExternaDTO();
        dto.setIsbn(isbn);

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";

            String jsonResponse = restTemplate.getForObject(url, String.class);

            if (jsonResponse == null || jsonResponse.equals("{}")) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            String key = "ISBN:" + isbn;
            JsonNode bookNode = root.get(key);

            if (bookNode == null) {
                return null;
            }

            if (bookNode.has("title")) {
                dto.setTitulo(bookNode.get("title").asText());
            }

            if (bookNode.has("authors") && bookNode.get("authors").isArray()) {
                JsonNode firstAuthor = bookNode.get("authors").get(0);
                if (firstAuthor != null && firstAuthor.has("name")) {
                    dto.setAutor(firstAuthor.get("name").asText());
                }
            }

            if (bookNode.has("publishers") && bookNode.get("publishers").isArray()) {
                JsonNode firstPublisher = bookNode.get("publishers").get(0);
                if (firstPublisher != null && firstPublisher.has("name")) {
                    dto.setEditorial(firstPublisher.get("name").asText());
                }
            } else if (bookNode.has("publisher")) {
                dto.setEditorial(bookNode.get("publisher").asText());
            }

            if (bookNode.has("publish_date")) {
                String fecha = bookNode.get("publish_date").asText();
                try {
                    dto.setAnio(Integer.parseInt(fecha.replaceAll("[^0-9]", "").substring(0, 4)));
                } catch (Exception ignored) {
                }
            }

            if (bookNode.has("description")) {
                JsonNode descNode = bookNode.get("description");
                if (descNode.isObject() && descNode.has("value")) {
                    dto.setDescripcion(descNode.get("value").asText());
                } else {
                    dto.setDescripcion(descNode.asText());
                }
            }

            if (bookNode.has("cover") && bookNode.get("cover").has("large")) {
                dto.setPortada(bookNode.get("cover").get("large").asText());
            } else if (bookNode.has("cover") && bookNode.get("cover").has("medium")) {
                dto.setPortada(bookNode.get("cover").get("medium").asText());
            }

        } catch (Exception e) {
            return null;
        }

        return dto;
    }
}
