package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="deer") String name, Model model) {
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
        model.addAttribute("name", list);
        return "hello";
    }

    @RequestMapping("/start")
    @ResponseBody
    String start() {
        return "Starting java!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
