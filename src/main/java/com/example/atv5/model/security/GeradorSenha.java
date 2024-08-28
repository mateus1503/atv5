package com.example.atv5.model.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

    public static void main(String[] args) {
        //solicitando o encode para 123
        System.out.println(new BCryptPasswordEncoder().encode(""));
        System.out.println(new BCryptPasswordEncoder().upgradeEncoding("$2a$10$ft2Rr1NbRAnudW06ngo/YOHVANRYCUpQr3E0BNWY1qyI4K.1j61Ti"));
    }

}