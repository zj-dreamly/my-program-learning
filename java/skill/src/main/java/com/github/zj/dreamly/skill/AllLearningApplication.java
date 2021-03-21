package com.github.zj.dreamly.skill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class AllLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllLearningApplication.class, args);
    }

}
