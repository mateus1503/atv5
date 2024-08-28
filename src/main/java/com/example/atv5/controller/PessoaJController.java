package com.example.atv5.controller;

import com.example.atv5.model.entity.*;
import com.example.atv5.model.repository.PessoaJRepository;
import com.example.atv5.model.repository.RoleRepository;
import com.example.atv5.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/form")
    public String form(PessoaJuridica pessoaJuridica){
        return "pessoaJuridica/form";
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid PessoaJuridica pessoaJuridica, BindingResult result){
        // Verifica se há erros de validação, incluindo os da entidade Usuario
        if (result.hasErrors()) {
            return new ModelAndView("/pessoaJuridica/form");
        }

        Usuario usuario = pessoaJuridica.getUsuario();

        // Verifica se o usuário já existe
        if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
            result.rejectValue("usuario.username", "error.usuario", "Usuário já existe");
            return new ModelAndView("/pessoaJuridica/form");
        }

        Role role = roleRepository.findByNome("ROLE_USER");

        usuario.setPessoa(pessoaJuridica);
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        usuario.getRoles().add(role);
        usuarioRepository.save(usuario);

        repository.save(pessoaJuridica);
        return new ModelAndView("redirect:/login");
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
