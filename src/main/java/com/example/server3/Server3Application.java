package com.example.server3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Server3Application {

    public static void main(String[] args) {
        SpringApplication.run(Server3Application.class, args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            System.out.println("Server3 server starting");
            Server3Service.InitializeFirstCooks();
            Server3Service.InitializeSecondCooks();
            for (String arg : args) {
                System.out.println(arg);
            }
        };
    }
}
