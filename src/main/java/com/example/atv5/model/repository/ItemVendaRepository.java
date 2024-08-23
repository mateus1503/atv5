package com.example.atv5.model.repository;

import com.example.atv5.model.entity.ItemVenda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemVendaRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(ItemVenda itemVenda){
        em.persist(itemVenda);
    }

    public List<ItemVenda> itensVenda(){
        Query query = em.createQuery("from ItemVenda");
        return query.getResultList();
    }

    public List<ItemVenda> itensPorIdVenda(Long idVenda){
        Query query = em.createQuery(
                "FROM ItemVenda itemVenda WHERE itemVenda.venda.id = :idDaVenda", ItemVenda.class);
        query.setParameter("idDaVenda", idVenda);
        return query.getResultList();
    }

    public ItemVenda itemVenda(Long id){
        return em.find(ItemVenda.class, id);
    }

    public void remove(Long id){
        ItemVenda itemVenda = em.find(ItemVenda.class, id);
        em.remove(itemVenda);
    }

    public void update(ItemVenda itemVenda){
        em.merge(itemVenda);
    }
}
