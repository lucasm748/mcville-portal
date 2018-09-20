/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.Setor;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class SetorDAO {

    private static SetorDAO instance;
    protected EntityManager entityManager;

    public static SetorDAO getInstance() {
        if (instance == null) {
            instance = new SetorDAO();
        }

        return instance;
    }

    public SetorDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Setor getById(final int id) {
        return entityManager.find(Setor.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Setor> findAll() {
        return entityManager.createQuery("FROM " + Setor.class.getName()).getResultList();
    }

    public List<Setor> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Setor.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Setor setor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(setor);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Setor setor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(setor);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Setor setor) {
        if (pode_remover(setor)) {
            try {
                entityManager.getTransaction().begin();
                setor = entityManager.find(Setor.class, setor.getId());
                entityManager.remove(setor);
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
            Setor setor = getById(id);
            remove(setor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Setor e) {
        int i = 0;
        List<Cidade> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cidade.class.getName()+" WHERE setor_id =" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }

}
