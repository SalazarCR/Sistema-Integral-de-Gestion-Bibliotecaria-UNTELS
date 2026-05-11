package pe.edu.untels.dtos;

public class LoginResponseDTO {

    private Boolean success;
    private String message;
    private String token;
    private String refreshToken;
    private UserInfo user;
    private String role;

    // Constructor vacío
    public LoginResponseDTO() {
    }

    // Constructor completo
    public LoginResponseDTO(Boolean success, String message, String token, String refreshToken, UserInfo user, String role) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
        this.role = role;
    }

    // Getters y Setters
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // ToString
    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", token='" + (token != null ? "***" : "null") + '\'' +
                ", refreshToken='" + (refreshToken != null ? "***" : "null") + '\'' +
                ", user=" + user +
                ", role='" + role + '\'' +
                '}';
    }

    // Clase interna: UserInfo (información mínima del usuario)
    public static class UserInfo {

        private Integer id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;

        // Constructor vacío
        public UserInfo() {
        }

        // Constructor completo
        public UserInfo(Integer id, String username, String email, String firstName, String lastName) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        // Getters y Setters
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        // ToString
        @Override
        public String toString() {
            return "UserInfo{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }
}

