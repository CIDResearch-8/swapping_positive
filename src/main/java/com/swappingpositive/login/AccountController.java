package com.swappingpositive.login;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

    @Autowired
    AccountService service;

    //フォームの入力を受け取るためにLoginFormインスタンスを渡す
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute(new LoginForm());
        return "login-form";
    }

    /* ログイン処理は実装しない。SpringSecurityの処理で行われる。
    @RequestMapping(value = "/login")
    public String login(@Valid LoginForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "login/login";
        }

        return "redirect:/menu";
    }
*/

    // SpringConfigで設定したログインできなかった場合の処理を定義する
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login-form";
    }

    //フォームの入力を受け取るためにRegisterFormインスタンスを渡す
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    //アカウント作成するための処理
    @PostMapping("/register")
    public String registerNew(@Validated RegisterForm registerForm, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            service.createAccount(registerForm);
        }
        catch (NullPointerException e) {
            return "redirect:/register-error";
        }
        return "redirect:/user/home";
    }

    //アカウントを作成出来なかった時の処理
    @RequestMapping("/register-error")
    public String registerError(Model model) {
        model.addAttribute("registerError", true);
        return register(model);
    }

    @RequestMapping("/config")
    public String config(Model model) {
        model.addAttribute("usernameForm", new UsernameForm());
        model.addAttribute("emailForm", new EmailForm());
        model.addAttribute("iconForm", new IconForm());
        return "config";
    }


    //ユーザーごとにアカウントを削除する処理
    @RequestMapping("/{userId}/delete")
    public String deleteAccount(@PathVariable String userId, @AuthenticationPrincipal LoginUser loginUser) {
        if (!loginUser.getUserId().equals(userId)) {
            return "error/user-not-found";
        }

        service.deleteAccount(userId);
        return "redirect:/login";
    }
}
