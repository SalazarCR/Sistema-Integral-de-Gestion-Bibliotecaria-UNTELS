package pe.edu.untels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiBibliotecaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBibliotecaApplication.class, args);
    }

}
