package com.example.atv5.controller;

import com.example.atv5.model.entity.Produto;
import com.example.atv5.model.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Transactional // Gerenciar transações
@Controller
@RequestMapping("produtos") // Prefixo de URL para todas as rotas de um Controller
public class ProdutoController {

    @Autowired // Fornecer uma instância
    ProdutoRepository produtoRepository;

    @GetMapping("/form")
    public String form(Produto produto){
        return "produtos/form";
    }

    @GetMapping("/list") // Rota de requisições GET
    public ModelAndView listar (ModelMap model) {
        // ModelMap é utilizado para fornecer uma visão com os dados passados (chave, valor)
        model.addAttribute("produtos", produtoRepository.produtos());
        return new ModelAndView("/produtos/list", model);
    }

    @GetMapping("/compraProdutos") // Rota de requisições GET
    public ModelAndView compraProdutos (ModelMap model, RedirectAttributes attributes) {
        // ModelMap é utilizado para fornecer uma visão com os dados passados (chave, valor)
        String mensagem = (String) attributes.getFlashAttributes().get("mensagem");
        model.addAttribute("produtos", produtoRepository.produtosAtivo());
        return new ModelAndView("/produtos/listAtivo", model);
    }

    @PostMapping("/save") // Rota de requisições POST
    public ModelAndView save(@Valid Produto produto, BindingResult result){
        if (result.hasErrors()) {
            return new ModelAndView("/produtos/form");
        }
        this.produtoRepository.save(produto);
        return new ModelAndView("redirect:/produtos/list");
    }

    @PostMapping("/buscar")
    public ModelAndView buscarProduto(@RequestParam("produto") String produto, ModelMap model){
        List<Produto> produtos = this.produtoRepository.buscarProdutos(produto);
        model.addAttribute("produtos", produtos);
        return new ModelAndView("/produtos/listAtivo", model);
    }

    // @PathVariable utilizada para resgatar o valor passado diretamente na URL
    @GetMapping("/alterarStatus/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        Produto produto = produtoRepository.findById(id);
        if (produto.getStatus() == Boolean.TRUE) {
            produto.setStatus(Boolean.FALSE);
        } else if (produto.getStatus() == Boolean.FALSE) {
            produto.setStatus(Boolean.TRUE);
        }
//        this.produtoRepository.update(produto);
        // O redirect não vai diretamente para a view
        // mas sim para um outro método ou Classe
        return new ModelAndView("redirect:/produtos/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit (@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("produto", produtoRepository.findById(id));
        return new ModelAndView("produtos/form", model); // Retorna uma página HTML
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/produtos/form");
        }
        this.produtoRepository.update(produto);
        return new ModelAndView("redirect:/produtos/list");
    }
}
