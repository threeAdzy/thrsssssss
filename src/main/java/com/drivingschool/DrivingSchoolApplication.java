package com.drivingschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableTransactionManagement
@EnableWebSocket
@ServletComponentScan
public class DrivingSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivingSchoolApplication.class, args);
    }

}
