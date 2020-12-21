package com.swappingpositive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swappingpositive.login.AccountService;
import com.swappingpositive.login.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    @Autowired
    private AccountService accountService;

    //フォームの入力を受け取るためにCommentFormインスタンスを渡す
    @GetMapping("/comment")
    public String comment(Model model) {
        model.addAttribute("commentForm", new CommentForm());
        return "comment-form";
    }

    //コメント作成するための処理
    @PostMapping("/comment")
    public String commentNew(@AuthenticationPrincipal LoginUser loginUser, @Validated CommentForm commentForm, BindingResult result) {
        if (result.hasErrors()) {
            return "comment-form";
        }

        try {
            commentService.save(commentForm, loginUser.getUserId());
        }
        catch (NullPointerException e) {
            return "redirect:/comment-error";
        }
        return "redirect:/user/home";
    }

    //コメントを作成出来なかった時の処理
    @RequestMapping("/comment-error")
    public String commentError(Model model) {
        model.addAttribute("commentError", true);
        return comment(model);
    }

    @GetMapping("/{userId}/comment/{commentId}")
    public String showComment(@PathVariable String userId, @PathVariable Integer commentId, Model model) {
        if (!commentService.findById(commentId).getUserId().equals(userId)) {
            return "error/comment-not-found";
        }
        model.addAttribute("comment", commentService.findById(commentId));
        model.addAttribute("replies", commentService.findByReplyParentId(commentId));
        return "show-comment";
    }

    @GetMapping("/user/home")
    public String showTimeline(Model model) {
        model.addAttribute("comments", commentService.findAll());
        return "timeline";
    }

}

@RestController
class RestCommentController {
    @Autowired
    private CommentService commentService;

    //フロントエンド側へuserIdを渡すための処理
    @GetMapping("rest-api/login-user-id/get")
    public String getLoginUserId(@AuthenticationPrincipal LoginUser loginUser) {
        return loginUser.getUserId();
    }

    @PostMapping(value="rest-api/comment/post", produces = "application/json")
    public void commentNew(@RequestBody String inputText) {
        //Ajax通信で受け取ったjsonをオブジェクトに変換
        ObjectMapper mapper = new ObjectMapper();
        AjaxCommentForm comment = null;
        try {
            comment = mapper.readValue(inputText, AjaxCommentForm.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //受け取ったオブジェクトをコメントとしてデータべースに挿入
        try {
            commentService.save(new CommentForm(Objects.requireNonNull(comment).getInputText()), comment.getUserId());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
