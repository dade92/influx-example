package webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"webapp", "adapters"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}