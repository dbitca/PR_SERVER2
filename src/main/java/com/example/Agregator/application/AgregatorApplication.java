package com.example.Agregator.application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgregatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            System.out.println("Agregator server starting");
            AgregatorService.InitializeConsumerThreads();
            AgregatorService.InitializeProducerThreads();
            for (String arg : args) {
                System.out.println(arg);
            }
        };
    }
}
