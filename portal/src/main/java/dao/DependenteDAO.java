/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.Dependente;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class DependenteDAO {

    private static DependenteDAO instance;
    protected EntityManager entityManager;

    public static DependenteDAO getInstance() {
        if (instance == null) {
            instance = new DependenteDAO();
        }

        return instance;
    }

    public DependenteDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Dependente getById(final int id) {
        return entityManager.find(Dependente.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Dependente> findAll() {
        return entityManager.createQuery("FROM " + Dependente.class.getName()).getResultList();
    }

    public List<Dependente> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Dependente.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Dependente dependente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(dependente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Dependente dependente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(dependente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Dependente dependente) {
        if (pode_remover(dependente)) {
            try {
                entityManager.getTransaction().begin();
                dependente = entityManager.find(Dependente.class, dependente.getId());
                entityManager.remove(dependente);
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
            Dependente dependente = getById(id);
            remove(dependente);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Dependente e) {
        int i = 0;
        List<Cidade> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cidade.class.getName()+" WHERE dependente_id =" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }

}
