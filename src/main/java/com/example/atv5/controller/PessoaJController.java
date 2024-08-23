package com.example.atv5.controller;

import com.example.atv5.model.entity.Pessoa;
import com.example.atv5.model.entity.PessoaFisica;
import com.example.atv5.model.entity.PessoaJuridica;
import com.example.atv5.model.repository.PessoaJRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
@RequestMapping("pessoaJuridica")
public class PessoaJController {

    @Autowired
    PessoaJRepository repository;

    @GetMapping("/form")
    public String form(PessoaJuridica pessoaJuridica){
        return "pessoaJuridica/form";
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid PessoaJuridica pessoa, BindingResult result){
        if(result.hasErrors()){
            return new ModelAndView("pessoaJuridica/form");
        }
        repository.save(pessoa);
        return new ModelAndView("redirect:/pessoaJuridica/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("pessoaJuridica", repository.findById(id));
        return new ModelAndView("/pessoaJuridica/form", model);
    }

    // @PathVariable utilizada para resgatar o valor passado diretamente na URL
    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        // O redirect não vai diretamente para a view
        // mas sim para um outro método ou Classe
        return new ModelAndView("redirect:/pessoa/list");
    }

    @PostMapping("/update")
    public ModelAndView updateF(PessoaJuridica pessoa) {
        this.repository.update(pessoa);
        return new ModelAndView("redirect:/pessoa/list");
    }
}
