package io.pillopl.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Cinema {

    public static void main(String[] args) {
        SpringApplication.run(Cinema.class, args);
    }


}
