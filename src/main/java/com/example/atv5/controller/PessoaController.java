package com.example.atv5.controller;

import com.example.atv5.model.entity.Pessoa;
import com.example.atv5.model.entity.PessoaFisica;
import com.example.atv5.model.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    PessoaRepository repository;

    @PostMapping("/buscar")
    public ModelAndView buscarPessoa(@RequestParam("pessoa") String pessoa, ModelMap model){
        List<Pessoa> pessoas = this.repository.buscarPessoas(pessoa);
        model.addAttribute("pessoas", pessoas);
        return new ModelAndView("/pessoa/list", model);
    }

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("msg","Lista de Pessoas");
        model.addAttribute("pessoas", repository.pessoas());
        return new ModelAndView("/pessoa/list", model); //caminho para a view
    }
}
