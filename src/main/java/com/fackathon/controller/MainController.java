package com.fackathon.controller;

import com.fackathon.domain.Account;
import com.fackathon.domain.MessagePopup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String getRegister(Model model) {
        model.addAttribute("account", new Account());
        return "register";
    }

    @PostMapping("/")
    public String register(Model model) {
        model.addAttribute("account", new Account());
        return "login";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("account", new Account());
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, Model model) {
        model.addAttribute("messagePopup", MessagePopup.success("You have just logged in 'username'!"));
        model.addAttribute("accountList", new ArrayList<>());
        return "accounts";
    }

    @PostMapping("/delete")
    public String deleteAccount(HttpSession session, @RequestParam String username, Model model) {
        model.addAttribute("messagePopup", MessagePopup.success("Consider something changed!"));
        model.addAttribute("accountList", new ArrayList<>());
        return "accounts";
    }

}
