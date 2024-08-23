package com.example.atv5.controller;

import com.example.atv5.model.entity.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "authenticator/login";
    }

    @GetMapping("/acesso_negado")
    public String acesso_negado(){
        return "authenticator/acesso_negado";
    }
}
