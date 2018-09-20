/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.Restricao;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class RestricaoDAO {

    private static RestricaoDAO instance;
    protected EntityManager entityManager;

    public static RestricaoDAO getInstance() {
        if (instance == null) {
            instance = new RestricaoDAO();
        }

        return instance;
    }

    public RestricaoDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Restricao getById(final int id) {
        return entityManager.find(Restricao.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Restricao> findAll() {
        return entityManager.createQuery("FROM " + Restricao.class.getName()).getResultList();
    }

    public List<Restricao> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Restricao.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Restricao restricao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(restricao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Restricao restricao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(restricao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Restricao restricao) {
        if (pode_remover(restricao)) {
            try {
                entityManager.getTransaction().begin();
                restricao = entityManager.find(Restricao.class, restricao.getId());
                entityManager.remove(restricao);
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
            Restricao restricao = getById(id);
            remove(restricao);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Restricao e) {
        int i = 0;
        List<Cidade> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cidade.class.getName()+" WHERE restricao_id =" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }

}
