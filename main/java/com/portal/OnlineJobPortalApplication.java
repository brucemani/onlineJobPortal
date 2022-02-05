package com.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlineJobPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineJobPortalApplication.class, args);

        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("admin");
        System.out.println("******************"+encode);
//        boolean decode = bCryptPasswordEncoder.matches("mani","$2a$10$Kgb2.FbNVUytN8eFfyOksOlwVQ8Ac7VJu8QlGG3gLZ3fQCY3Y0T8m");
//        System.out.println("*****************"+encode);
//        System.out.println("*****************"+decode);
//        $2a$10$Kgb2.FbNVUytN8eFfyOksOlwVQ8Ac7VJu8QlGG3gLZ3fQCY3Y0T8m
    }

}
