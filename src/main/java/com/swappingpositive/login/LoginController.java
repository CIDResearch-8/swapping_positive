package com.swappingpositive.login;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    AccountService service;

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

    @GetMapping("/resister")
    public String resister(Model model) {
        model.addAttribute("resisterForm", new ResisterForm());
        return "resister";
    }

    @PostMapping("/resister")
    public String resisterNew(@Validated ResisterForm resisterForm, BindingResult result) {
        if (result.hasErrors()) {
            return "resister";
        }

        service.createAccount(resisterForm);
        return "redirect:/user/home";
    }

}
