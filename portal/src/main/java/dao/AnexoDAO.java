/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.Anexo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class AnexoDAO {

    private static AnexoDAO instance;
    protected EntityManager entityManager;

    public static AnexoDAO getInstance() {
        if (instance == null) {
            instance = new AnexoDAO();
        }

        return instance;
    }

    public AnexoDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Anexo getById(final int id) {
        return entityManager.find(Anexo.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Anexo> findAll() {
        return entityManager.createQuery("FROM " + Anexo.class.getName()).getResultList();
    }

    public List<Anexo> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Anexo.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Anexo anexo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(anexo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Anexo anexo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(anexo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Anexo anexo) {
        if (pode_remover(anexo)) {
            try {
                entityManager.getTransaction().begin();
                anexo = entityManager.find(Anexo.class, anexo.getId());
                entityManager.remove(anexo);
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
            Anexo anexo = getById(id);
            remove(anexo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Anexo e) {
        int i = 0;
        List<Cidade> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cidade.class.getName()+" WHERE anexo_id=" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }

}
