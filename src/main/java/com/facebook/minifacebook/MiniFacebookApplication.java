package com.facebook.minifacebook;

import com.facebook.minifacebook.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication

public class MiniFacebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniFacebookApplication.class, args);
    }


}
