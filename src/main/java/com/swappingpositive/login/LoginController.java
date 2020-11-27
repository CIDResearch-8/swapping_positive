package com.swappingpositive.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
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
}
