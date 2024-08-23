package com.example.atv5.model.repository;

import com.example.atv5.model.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Usuario findByUsername(String username) {
        TypedQuery<Usuario> query = entityManager.createQuery(
                "SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst().orElse(null);
    }
    public void updatePassword(String username, String newPassword) {
        Usuario usuario = findByUsername(username);
        if (usuario != null) {
            usuario.setPassword(newPassword);
            entityManager.merge(usuario);
        }
    }

    public void save(Usuario usuario) {
        entityManager.persist(usuario);
    }

    public Usuario findById(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    public void delete(Usuario usuario) {
        if (entityManager.contains(usuario)) {
            entityManager.remove(usuario);
        } else {
            entityManager.remove(entityManager.merge(usuario));
        }
    }

    public void update(Usuario usuario) {
        entityManager.merge(usuario);
    }

    public List<Usuario> findAll() {
        TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class);
        return query.getResultList();
    }
}
