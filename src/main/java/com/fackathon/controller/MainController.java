package com.fackathon.controller;

import com.fackathon.domain.Account;
import com.fackathon.domain.MessagePopup;
import com.fackathon.domain.Rate;
import com.fackathon.service.RateCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final RateCalculationService rateCalculationService;

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

    @GetMapping("/websites")
    public String getWebsites(Model model) {
        model.addAttribute("hostsList", new ArrayList<>());
        return "websites";
    }

    @GetMapping("/rates")
    public String getRates(Model model) {
        List<List<Rate>> metacriticRates = rateCalculationService.computeMetacriticRates();
        List<List<Rate>> imdbRates = rateCalculationService.computeImdbRates();
        if (metacriticRates != null && imdbRates != null) {
            model.addAttribute("metaRateList", metacriticRates.get(0));
            // FIXME: get average trust rating for all elements in list and return a single List<Rate>
            model.addAttribute("imdbRateList", imdbRates);
        } else {
            model.addAttribute("messagePopup", MessagePopup.success("I did not like your Json!"));
            model.addAttribute("rateList", new ArrayList<>());
        }
        return "rates";
    }

}
