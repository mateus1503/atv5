package com.example.atv5.model.repository;

import com.example.atv5.model.entity.Pessoa;
import com.example.atv5.model.entity.PessoaFisica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaFRepository {

    @PersistenceContext  //informa ao container do Spring a responsabilidade de gerenciar a dependência
    private EntityManager em;


    public void savePessoaFisica(PessoaFisica pessoaFisica){
        em.persist(pessoaFisica);
    }

    public PessoaFisica findById(Long id){
        return em.find(PessoaFisica.class, id);
    }

    public List<PessoaFisica> pessoasFisica(){
        Query query = em.createQuery("from PessoaFisica");
        return query.getResultList();
    }

    public List<PessoaFisica> buscarpessoaFisica(String nome){
        Query query = em.createQuery("from PessoaFisica where nome like :nome");
        query.setParameter("nome", "%"+nome+"%");
        return query.getResultList();
    }

    public void remove(Long id){
        PessoaFisica pessoaFisica = em.find(PessoaFisica.class, id);
        em.remove(pessoaFisica);
    }

    public void update(PessoaFisica pessoaFisica){
        em.merge(pessoaFisica);
    }

//    public List<Pessoa> buscarPessoas(String pessoa) {
//        Query query = em.createQuery("from Pessoa where lower(nome) like :pessoa or" +
//                " lower(telefone) like :pessoa");
//        query.setParameter("pessoa", "%" + pessoa + "%");
//        return query.getResultList();
//    }
}