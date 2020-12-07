package com.swappingpositive;

import com.swappingpositive.login.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommentController {
    
    @Autowired
    private CommentService service;

    //フォームの入力を受け取るためにCommentFormインスタンスを渡す
    @GetMapping("/comment")
    public String comment(Model model) {
        model.addAttribute("commentForm", new CommentForm());
        return "comment-form";
    }

    //アカウント作成するための処理
    @PostMapping("/comment")
    public String commentNew(@AuthenticationPrincipal LoginUser loginUser, @Validated CommentForm commentForm, BindingResult result) {
        if (result.hasErrors()) {
            return "comment-form";
        }

        try {
            service.save(commentForm, loginUser.getUserId());
        }
        catch (NullPointerException e) {
            return "redirect:/comment-error";
        }
        return "redirect:/user/home";
    }

    //アカウントを作成出来なかった時の処理
    @RequestMapping("/comment-error")
    public String commentError(Model model) {
        model.addAttribute("commentError", true);
        return comment(model);
    }
}
