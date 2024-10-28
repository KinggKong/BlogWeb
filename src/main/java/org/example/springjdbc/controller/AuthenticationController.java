package org.example.springjdbc.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjdbc.model.AuthenRequest;
import org.example.springjdbc.dto.AccountResponse;
import org.example.springjdbc.service.impl.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthenticationController {
    AccountService accountService;
    HttpSession session;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("authenRequest", new AuthenRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("authenRequest") AuthenRequest authenRequest, Model model) {
        AccountResponse accountResponse = accountService.findByUserName(authenRequest.getUsername());
        if (accountResponse == null) {
            return "login";
        }
        if (accountResponse.getPassword().equals(authenRequest.getPassword())) {
            session.setAttribute("user", accountResponse);
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        session.invalidate();
        return "redirect:/home";
    }
}
