package com.swappingpositive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class Controllers {

    //DB操作をするための処理
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Controllers(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // / -> index.html
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    // /login -> login.html
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // /user/ 以下はUsersクラスを介する
    @Controller
    @RequestMapping("/user")
    static class Users {

        // /user/<ユーザーID> -> user/mypage.html
        @RequestMapping("/{userId}")
        public String mypage() {
            return "user/mypage";
        }

        // /user/home -> user/home.html
        @RequestMapping("/home")
        public String home() {
            return "user/home";
        }

        // /user/
        @RequestMapping("/{commentId}")
        public String comment() {
            return "user/comment";
        }
    }
}
