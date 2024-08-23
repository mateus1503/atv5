package com.example.atv5.model.repository;

import com.example.atv5.model.entity.Produto;
import com.example.atv5.model.entity.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Produto produto){
        em.persist(produto);
    }

    public List<Produto> produtos(){
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }

    public List<Produto> produtosAtivo(){
        Query query = em.createQuery("from Produto produto where produto.status=true");
        return query.getResultList();
    }

    public List<Produto> buscarProdutos(String produto) {
        produto = produto.toLowerCase();
        Query query = em.createQuery("from Produto produto where lower(produto.descricao) like :produto", Produto.class);
        query.setParameter("produto", "%" + produto + "%");
        return query.getResultList();
    }

    public Produto findById(Long id){
        return em.find(Produto.class, id);
    }

    public void remove(Long id){
        Produto produto = em.find(Produto.class, id);
    }

    public void update(Produto produto){
        em.merge(produto);
    }
}
