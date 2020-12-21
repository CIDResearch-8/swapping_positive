package com.swappingpositive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class Controllers {

    // / -> index.html
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    //./confirmにアクセスし、フォームを送信した時
    @GetMapping("/confirm")
    //今回は受け取る側になる
    public String confirm(Model model) {
        //return文に書かれた値と同じ@PostMappingがある場合、そちらのメソッドに遷移する
        return "result";
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


        // /user/
        @RequestMapping("/{commentId}")
        public String comment() {
            return "user/comment";
        }
    }
}
