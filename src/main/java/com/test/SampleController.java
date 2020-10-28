package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@EnableAutoConfiguration
public class SampleController {

    List<Post> posts = new ArrayList<>();

    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="deer") String name, Model model) {
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
        model.addAttribute("name", list);
        return "hello";
    }

    @RequestMapping("/start")
    String start(Model model) {
        String str = "Starting java";
        model.addAttribute("str", str);
        return "start";
    }

    @GetMapping("/confirm")
    public String confirm(Model model) {
        model.addAttribute("post", new Post());
        Post.nextPostId();
        return "confirm";
    }

    @PostMapping("/confirm")
    public String confirmSubmit(@ModelAttribute Post post, Model model) {
        model.addAttribute("post", post);
        posts.add(post);
        model.addAttribute("posts", posts);
        return "result";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
