package pe.edu.untels.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        String rawPassword = "12345";

        String encoded = new BCryptPasswordEncoder().encode(rawPassword);

        System.out.println("BCrypt password:");
        System.out.println(encoded);
    }
}