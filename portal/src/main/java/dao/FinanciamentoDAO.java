/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.Cliente;
import entity.Financiamento;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FinanciamentoDAO {

    private static FinanciamentoDAO instance;
    protected EntityManager entityManager;

    public static FinanciamentoDAO getInstance() {
        if (instance == null) {
            instance = new FinanciamentoDAO();
        }

        return instance;
    }

    public FinanciamentoDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Financiamento getById(final int id) {
        return entityManager.find(Financiamento.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Financiamento> findAll() {
        return entityManager.createQuery("FROM " + Financiamento.class.getName()).getResultList();
    }

    public List<Financiamento> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Financiamento.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Financiamento financiamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(financiamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Financiamento financiamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(financiamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Financiamento financiamento) {
        if (pode_remover(financiamento)) {
            try {
                entityManager.getTransaction().begin();
                financiamento = entityManager.find(Financiamento.class, financiamento.getId());
                entityManager.remove(financiamento);
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
            Financiamento financiamento = getById(id);
            remove(financiamento);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Financiamento e) {
        int i = 0;
        List<Cliente> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cliente.class.getName()+" WHERE financiamento_id =" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }

}
