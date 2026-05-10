package pe.edu.untels.dtos;

public class ToggleResponseDTO {
    private int idUser;
    private String usernameUser;
    private boolean statusUser;

    public ToggleResponseDTO(int idUser, String usernameUser, boolean statusUser) {
        this.idUser = idUser;
        this.usernameUser = usernameUser;
        this.statusUser = statusUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        this.usernameUser = usernameUser;
    }

    public boolean isStatusUser() {
        return statusUser;
    }

    public void setStatusUser(boolean statusUser) {
        this.statusUser = statusUser;
    }
}

