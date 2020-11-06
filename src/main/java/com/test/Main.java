package com.test;

import org.springframework.boot.SpringApplication;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(SampleController.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "9000"));
        app.run(args);
    }
}
