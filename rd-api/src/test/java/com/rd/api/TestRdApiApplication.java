package com.rd.api;

import org.springframework.boot.SpringApplication;

public class TestRdApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(RdApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
