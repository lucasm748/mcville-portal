/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.Estado;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class EstadoDAO {

    private static EstadoDAO instance;
    protected EntityManager entityManager;

    public static EstadoDAO getInstance() {
        if (instance == null) {
            instance = new EstadoDAO();
        }

        return instance;
    }

    public EstadoDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Estado getById(final int id) {
        return entityManager.find(Estado.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Estado> findAll() {
        return entityManager.createQuery("FROM " + Estado.class.getName()).getResultList();
    }

    public List<Estado> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Estado.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Estado estado) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(estado);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Estado estado) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(estado);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Estado estado) {
        if (pode_remover(estado)) {
            try {
                entityManager.getTransaction().begin();
                estado = entityManager.find(Estado.class, estado.getId());
                entityManager.remove(estado);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                entityManager.getTransaction().rollback();
            }
            return 1;

        } else {
            return 0;
        }
    }

    public void removeById(final int id) {
        try {
            Estado estado = getById(id);
            remove(estado);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Estado e) {
        int i = 0;
        List<Cidade> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cidade.class.getName()+" WHERE estado_id =" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }

}
