package pe.edu.untels.dtos;

public class CarreraDTO {
    private int idCarrera;
    private String nameCarrera;
    private String descriptionCarrera;
    private boolean statusCarrera;

    public CarreraDTO() {}

    public CarreraDTO(int idCarrera, String nameCarrera, String descriptionCarrera, boolean statusCarrera) {
        this.idCarrera = idCarrera;
        this.nameCarrera = nameCarrera;
        this.descriptionCarrera = descriptionCarrera;
        this.statusCarrera = statusCarrera;
    }

    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }
    public String getNameCarrera() { return nameCarrera; }
    public void setNameCarrera(String nameCarrera) { this.nameCarrera = nameCarrera; }
    public String getDescriptionCarrera() { return descriptionCarrera; }
    public void setDescriptionCarrera(String descriptionCarrera) { this.descriptionCarrera = descriptionCarrera; }
    public boolean isStatusCarrera() { return statusCarrera; }
    public void setStatusCarrera(boolean statusCarrera) { this.statusCarrera = statusCarrera; }
}

