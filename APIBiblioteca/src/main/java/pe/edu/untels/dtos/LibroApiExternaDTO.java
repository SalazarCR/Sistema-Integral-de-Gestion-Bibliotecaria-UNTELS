package pe.edu.untels.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para mapear respuestas de la API externa OpenLibrary.
 * Se utiliza para enriquecer información de libros consultando:
 * https://openlibrary.org/api/books?bibkeys=ISBN:...&format=json&jscmd=data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroApiExternaDTO {

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("authors")
    private List<AutorOpenLibrary> autores;

    @JsonProperty("publishers")
    private List<PublisherOpenLibrary> editoriales;

    @JsonProperty("publish_date")
    private String fechaPublicacion;

    @JsonProperty("description")
    private String descripcion;

    @JsonProperty("number_of_pages")
    private Integer paginas;

    @JsonProperty("cover")
    private CoverOpenLibrary portada;

    @JsonProperty("isbn_10")
    private List<String> isbn10;

    @JsonProperty("isbn_13")
    private List<String> isbn13;

    @JsonProperty("languages")
    private List<LanguageOpenLibrary> idiomas;

    /**
     * DTO interno para manejar autores de OpenLibrary
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AutorOpenLibrary {
        @JsonProperty("name")
        private String nombre;

        @JsonProperty("url")
        private String url;
    }

    /**
     * DTO interno para manejar editoriales de OpenLibrary
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PublisherOpenLibrary {
        @JsonProperty("name")
        private String nombre;
    }

    /**
     * DTO interno para manejar portadas de OpenLibrary
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CoverOpenLibrary {
        @JsonProperty("small")
        private String pequena;

        @JsonProperty("medium")
        private String media;

        @JsonProperty("large")
        private String grande;
    }

    /**
     * DTO interno para manejar idiomas de OpenLibrary
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LanguageOpenLibrary {
        @JsonProperty("key")
        private String clave;

        @JsonProperty("name")
        private String nombre;
    }
}

