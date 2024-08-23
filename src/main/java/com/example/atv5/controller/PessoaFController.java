package com.example.atv5.controller;

import com.example.atv5.model.entity.Pessoa;
import com.example.atv5.model.entity.PessoaFisica;
import com.example.atv5.model.entity.Role;
import com.example.atv5.model.entity.Usuario;
import com.example.atv5.model.repository.PessoaFRepository;
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
@RequestMapping("pessoaFisica")
public class PessoaFController {

    @Autowired
    PessoaFRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/form")
    public String form(PessoaFisica pessoaFisica){
        return "pessoaFisica/form";
    }

    @PostMapping("/save")
    public ModelAndView save(PessoaFisica pessoaFisica){
//        if (result.hasErrors()) {
//            return new ModelAndView("/pessoaFisica/form");
//        }
        Role role = roleRepository.findByNome("ROLE_USER");

        Usuario usuario = new Usuario();
        usuario.setPessoa(pessoaFisica);
        usuario.setUsername(pessoaFisica.getUsuario().getUsername());
        usuario.setPassword(new BCryptPasswordEncoder().encode(pessoaFisica.getUsuario().getPassword()));
        usuario.getRoles().add(role);
        usuarioRepository.save(usuario);

        repository.savePessoaFisica(pessoaFisica);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("pessoaFisica", repository.findById(id));
        return new ModelAndView("/pessoaFisica/form", model);
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
    public ModelAndView update(PessoaFisica pessoa) {
        this.repository.update(pessoa);
        return new ModelAndView("redirect:/pessoa/list");
    }
}
