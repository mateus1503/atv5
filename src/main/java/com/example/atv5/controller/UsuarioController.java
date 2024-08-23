package com.example.atv5.controller;

import com.example.atv5.model.repository.UsuarioRepository;
import com.example.atv5.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public String listUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuario/list";
    }

    @GetMapping("/form")
    public String formUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/form";
    }

    @PostMapping("/save")
    public String saveUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
        return "redirect:/login";
    }

    @GetMapping("/edit/{id}")
    public String editUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            model.addAttribute("errorMessage", "Usuário não encontrado.");
            return "redirect:/usuario";
        }
        model.addAttribute("usuario", usuario);
        return "usuario/form";
    }


    @GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {

            redirectAttributes.addFlashAttribute("errorMessage", "Usuário não encontrado.");
            return "redirect:/usuario/list";
        }
        usuarioRepository.delete(usuario);
        return "redirect:/usuario/list";
    }

}
