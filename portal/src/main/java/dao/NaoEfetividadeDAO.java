/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.NaoEfetividade;
import entity.NaoEfetividade;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class NaoEfetividadeDAO {

    private static NaoEfetividadeDAO instance;
    protected EntityManager entityManager;

    public static NaoEfetividadeDAO getInstance() {
        if (instance == null) {
            instance = new NaoEfetividadeDAO();
        }

        return instance;
    }

    public NaoEfetividadeDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public NaoEfetividade getById(final int id) {
        return entityManager.find(NaoEfetividade.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<NaoEfetividade> findAll() {
        return entityManager.createQuery("FROM " + NaoEfetividade.class.getName()).getResultList();
    }

    public List<NaoEfetividade> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + NaoEfetividade.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(NaoEfetividade naoEfetividade) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(naoEfetividade);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(NaoEfetividade naoEfetividade) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(naoEfetividade);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(NaoEfetividade status) {
        if (pode_remover(status)) {
            try {
                entityManager.getTransaction().begin();
                status = entityManager.find(NaoEfetividade.class, status.getId());
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
            NaoEfetividade status = getById(id);
            remove(status);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(NaoEfetividade e) {
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
