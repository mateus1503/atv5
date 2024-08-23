package com.example.atv5.controller;

import com.example.atv5.model.entity.ItemVenda;
import com.example.atv5.model.repository.ItemVendaRepository;
import com.example.atv5.model.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Transactional // Gerenciar transações
@Controller
@RequestMapping("itemVenda") // Prefixo de URL para todas as rotas de um Controller
public class ItemVendaController {

    @Autowired // Fornecer uma instância
    ItemVendaRepository itemVenda;

    @Autowired // Fornecer uma instância
    VendaRepository vendaRepository;

    @GetMapping("/form")
    public String form(ItemVenda itemVenda){
        return "/form";
    }

    @GetMapping("/list") // Rota de requisições GET
    public ModelAndView listar (ModelMap model) {
        // ModelMap é utilizado para fornecer uma visão com os dados passados (chave, valor)
        model.addAttribute("itensVenda", itemVenda.itensVenda());
        return new ModelAndView("/venda/detail", model);
    }

    @PostMapping("/save") // Rota de requisições POST
    public ModelAndView save(ItemVenda itemVenda){
        this.itemVenda.save(itemVenda);
        return new ModelAndView("redirect:/itemVenda/list");
    }

    // @PathVariable utilizada para resgatar o valor passado diretamente na URL
    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        itemVenda.remove(id);
        // O redirect não vai diretamente para a view
        // mas sim para um outro método ou Classe
        return new ModelAndView("redirect:/itemVenda/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit (@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("produto", itemVenda.itemVenda(id));
        return new ModelAndView("/form", model); // Retorna uma página HTML
    }

    @PostMapping("/update")
    public ModelAndView update(ItemVenda itemVenda) {
        this.itemVenda.update(itemVenda);
        return new ModelAndView("redirect:/itemVenda/list");
    }
}
