package com.example.atv5.controller;

import com.example.atv5.model.entity.*;
import com.example.atv5.model.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Transactional // Gerenciar transações
@Scope("request")
@Controller
@RequestMapping("venda") // Prefixo de URL para todas as rotas de um Controller
public class VendaController {

    @Autowired // Fornecer uma instância
    VendaRepository repository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    PessoaJRepository pessoaJuridicaRepository;

    @Autowired
    PessoaFRepository pessoaFisicaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    Venda venda;

    @GetMapping("/form")
    public String form(Venda venda){
        return "/form";
    }

    @GetMapping("/list") // Rota de requisições GET
    public ModelAndView listar (ModelMap model) {
        // ModelMap é utilizado para fornecer uma visão com os dados passados (chave, valor)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("vendas", repository.vendas());
        } else {
            Pessoa pessoa = usuario.getPessoa();
            model.addAttribute("vendas", repository.findSalebyUser(pessoa));
        }
        return new ModelAndView("/venda/list", model);
    }

    @PostMapping("/buscar")
    public ModelAndView buscarVenda(@RequestParam("busca") String busca, ModelMap model) {
        List<Venda> vendas;

        if (!busca.isEmpty()) {
                vendas = repository.buscarNome(busca);
        } else {
            vendas = repository.vendas();
        }

        model.addAttribute("vendas", vendas);
        return new ModelAndView("/venda/list", model);  // Caminho da view
    }

    @PostMapping("/filtrarPorData")
    public ModelAndView filtrarPorData(@RequestParam("dataInicio") LocalDate dataInicio,
                                       @RequestParam("dataFim") LocalDate dataFim, ModelMap model) {
        List<Venda> vendas;

        if (dataInicio != null && dataFim != null) {
            if (dataInicio.isAfter(dataFim)) {
                model.addAttribute("mensagem", "Data início não pode ser após data fim");
                vendas = repository.vendas();
                model.addAttribute("vendas", vendas);
                return new ModelAndView("/venda/list", model);
            }
            vendas = repository.filtrarPorData(dataInicio, dataFim);
        } else {
            model.addAttribute("mensagem", "Os dois campos precisa ser preenchidos");
            vendas = repository.vendas();
            model.addAttribute("vendas", vendas);
            return new ModelAndView("/venda/list", model);
        }

        model.addAttribute("vendas", vendas);
        return new ModelAndView("/venda/list", model);  // Caminho da view
    }

    @GetMapping("/itemVenda/add/{id}")
    public ModelAndView produtoAdd(@PathVariable("id") Long produtoId){
        List<ItemVenda> itensVenda = venda.getItensVenda();

        boolean itemExists = false;
        for (ItemVenda itemVenda : itensVenda) {
            if (itemVenda.getProduto().getId().equals(produtoId)) {
                itemVenda.setQuantidade(itemVenda.getQuantidade() + 1);
                itemVenda.setTotalItem(itemVenda.getTotalItem().add(itemVenda.getProduto().getValor()));
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            Produto produto = produtoRepository.findById(produtoId);
            ItemVenda itemNovo = new ItemVenda();
            itemNovo.setProduto(produto);
            itemNovo.setQuantidade(1);
            itemNovo.setTotalItem(produto.getValor());
            itemNovo.setVenda(venda);
            itensVenda.add(itemNovo);
        }

        return new ModelAndView("redirect:/produtos/compraProdutos");
    }

    @GetMapping("/itemVenda/del/{id}")
    public ModelAndView produtoDel(@PathVariable("id") Long produtoId){
        List<ItemVenda> itensVenda = venda.getItensVenda();
        for (ItemVenda itemVenda : itensVenda) {
            if (itemVenda.getQuantidade() == 1) {
                itensVenda.remove(itemVenda);
                break;
            } else if (itemVenda.getProduto().getId().equals(produtoId)) {
                itemVenda.setQuantidade(itemVenda.getQuantidade() - 1);
                itemVenda.setTotalItem(itemVenda.getTotalItem().subtract(itemVenda.getProduto().getValor()));
                break;
            }
        }
        return new ModelAndView("redirect:/venda/carrinho");
    }

    @PostMapping("/save")
    public ModelAndView save(HttpSession session, RedirectAttributes attributes) {
        if (venda.getItensVenda().isEmpty()) {
            attributes.addFlashAttribute("mensagem", "Por favor, adicione itens ao carrinho!");
            return new ModelAndView("redirect:/produtos/compraProdutos");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario usuario = usuarioRepository.findByUsername(username);
        Pessoa pessoa = usuario.getPessoa();

        this.venda.setPessoa(pessoa);
        this.venda.setData(LocalDate.now());
        repository.save(venda);
        Long vendaId = venda.getId();

        session.removeAttribute("venda");

        return new ModelAndView("redirect:/venda/" + vendaId);
    }

    @GetMapping("/{id}")
    public ModelAndView listarVenda (@PathVariable("id") Long idVenda, ModelMap model) {
        model.addAttribute("venda", repository.venda(idVenda));
        return new ModelAndView("/itemVenda/detail", model); // Retorna uma página HTML
    }

    // @PathVariable utilizada para resgatar o valor passado diretamente na URL
    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        repository.remove(id);
        // O redirect não vai diretamente para a view
        // mas sim para um outro método ou Classe
        return new ModelAndView("redirect:/venda/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit (@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("venda", repository.venda(id));
        return new ModelAndView("/form", model); // Retorna uma página HTML
    }

    @PostMapping("/update")
    public ModelAndView update(Venda venda) {
        this.repository.update(venda);
        return new ModelAndView("redirect:/venda/list");
    }

    @GetMapping("/carrinho")
    public ModelAndView carrinho(ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());

        model.addAttribute("pessoasFisica", pessoaFisicaRepository.pessoasFisica());
        model.addAttribute("pessoasJuridica", pessoaJuridicaRepository.pessoaJuridica());
        return new ModelAndView("/venda/carrinho", model);
    }

    private Pessoa buscarPessoaPorId(Long id){
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id);
        if (pessoaFisica != null) {
            return pessoaFisica;
        }

        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id);
        if (pessoaJuridica != null) {
            return pessoaJuridica;
        }

        return null;
    }
}
