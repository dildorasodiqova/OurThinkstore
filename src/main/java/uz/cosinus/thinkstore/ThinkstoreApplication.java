package uz.cosinus.thinkstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yaml")
public class ThinkstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkstoreApplication.class, args);
    }

}
