package com.example.atv5.model.repository;

import com.example.atv5.model.entity.Pessoa;
import com.example.atv5.model.entity.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Repository
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Venda venda){
        System.out.println("Entrei no save");
        em.persist(venda);
    }

    public List<Venda> vendas(){
        Query query = em.createQuery("from Venda");
        return query.getResultList();
    }

    public List<Venda> findSalebyUser(Pessoa pessoa){
        Query query = em.createQuery("from Venda where pessoa.nome like :nome");
        query.setParameter("nome", pessoa.getNome());
        return query.getResultList();
    }

    public Venda venda(Long id){
        return em.find(Venda.class, id);
    }

    public List<Venda> filtrarPorData(LocalDate dataInicio, LocalDate dataFim) {
        // Consulta JPQL para buscar vendas com base na data
        Query query = em.createQuery("from Venda venda where venda.data between :dataInicio and :dataFim");
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);
        return query.getResultList();
    }

    public List<Venda> buscarNome(String nome) {
        nome = nome.toLowerCase();
        // Consulta JPQL para buscar vendas com base no nome da pessoa associada
        Query query = em.createQuery("from Venda venda where lower(venda.pessoa.nome) like :nome");
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    public void remove(Long id){
        Venda venda = em.find(Venda.class, id);
        em.remove(venda);
    }

    public void update(Venda venda){
        em.merge(venda);
    }
}
