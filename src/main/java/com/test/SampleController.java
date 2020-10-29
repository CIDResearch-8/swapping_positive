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

    //./helloにアクセスされた時
    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="deer") String name, Model model) {
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G"));
        //html(view)側へlistを"name"として同期する
        model.addAttribute("name", list);
        //hello.htmlを読み込む
        return "hello";
    }

    //./startにアクセスされた時
    @RequestMapping("/start")
    String start(Model model) {
        String str = "Starting java";
        //html(view)側へstrを"str"として同期する
        model.addAttribute("str", str);
        //start.htmlを読み込む
        return "start";
    }

    //./confirmにアクセスし、フォームを送信した時
    @GetMapping("/confirm")
    //今回は受け取る側になる
    public String confirm(Model model) {
        //html(view)側から取得した"post"をPostインスタンスとして生成
        model.addAttribute("post", new Post());
        //postIdを次番にするため実行
        Post.nextPostId();
        //return文に書かれた値と同じ@PostMappingがある場合、そちらのメソッドに遷移する
        return "confirm";
    }

    //./confirmにアクセスし、フォームを送信した後の遷移
    @PostMapping("/confirm")
    //時系列は@GetMapping → @PostMappingとなる
    public String confirmSubmit(@ModelAttribute Post post, Model model) {
        //html(view)側へ、@GetMapping時に生成したPostインスタンスを"post"として同期
        model.addAttribute("post", post);
        //listに追加(サーバーを止めない限りは残る)
        posts.add(post);
        ///html(view)側へpostsを"posts"として同期
        model.addAttribute("posts", posts);
        //result.htmlを読み込む
        return "result";
    }
}
