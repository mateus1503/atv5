package com.example.atv5.model.repository;

import com.example.atv5.model.entity.PessoaJuridica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaJRepository {

    @PersistenceContext  //informa ao container do Spring a responsabilidade de gerenciar a dependência
    private EntityManager em;

    public void save(PessoaJuridica pessoaJuridica){
        em.persist(pessoaJuridica);
    }

    public PessoaJuridica findById(Long id){
        return em.find(PessoaJuridica.class, id);
    }

    public List<PessoaJuridica> pessoaJuridica(){
        Query query = em.createQuery("from PessoaJuridica");
        return query.getResultList();
    }

    public List<PessoaJuridica> buscarpessoaJuridica(String nome){
        Query query = em.createQuery("from PessoaJuridica where nome like :nome");
        query.setParameter("nome", "%"+nome+"%");
        return query.getResultList();
    }

    public void remove(Long id){
        PessoaJuridica pessoaJuridica = em.find(PessoaJuridica.class, id);
        em.remove(pessoaJuridica);
    }

    public void update(PessoaJuridica pessoaJuridica){
        em.merge(pessoaJuridica);
    }
}

//    public List<Pessoa> buscarPessoas(String pessoa) {
//        Query query = em.createQuery("from Pessoa where lower(nome) like :pessoa or" +
//                " lower(telefone) like :pessoa");
//        query.setParameter("pessoa", "%" + pessoa + "%");
//        return query.getResultList();
//    }