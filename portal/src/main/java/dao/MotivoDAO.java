/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.Motivo;
import entity.Motivo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class MotivoDAO {

    private static MotivoDAO instance;
    protected EntityManager entityManager;

    public static MotivoDAO getInstance() {
        if (instance == null) {
            instance = new MotivoDAO();
        }

        return instance;
    }

    public MotivoDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Motivo getById(final int id) {
        return entityManager.find(Motivo.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Motivo> findAll() {
        return entityManager.createQuery("FROM " + Motivo.class.getName()).getResultList();
    }

    public List<Motivo> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Motivo.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Motivo motivo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(motivo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Motivo motivo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(motivo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Motivo status) {
        if (pode_remover(status)) {
            try {
                entityManager.getTransaction().begin();
                status = entityManager.find(Motivo.class, status.getId());
                entityManager.remove(status);
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
            Motivo status = getById(id);
            remove(status);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Motivo e) {
        int i = 0;
        List<Cidade> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cidade.class.getName()+" WHERE status_id =" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }
}
