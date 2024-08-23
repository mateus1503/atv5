package com.example.atv5.model.repository;

import com.example.atv5.model.entity.Produto;
import com.example.atv5.model.entity.Role;
import com.example.atv5.model.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Produto produto){
        em.persist(produto);
    }

    public List<Produto> produtos(){
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }

    public Role findByNome(String nome){
        TypedQuery<Role> query = em.createQuery("from Role role where nome like :nome", Role.class);
        query.setParameter("nome", nome);
        return query.getResultStream().findFirst().orElse(null);
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
