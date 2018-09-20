/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import entity.Empresa;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class EmpresaDAO {

    private static EmpresaDAO instance;
    protected EntityManager entityManager;

    public static EmpresaDAO getInstance() {
        if (instance == null) {
            instance = new EmpresaDAO();
        }

        return instance;
    }

    public EmpresaDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Empresa getById(final int id) {
        return entityManager.find(Empresa.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Empresa> findAll() {
        return entityManager.createQuery("FROM " + Empresa.class.getName()).getResultList();
    }

    public List<Empresa> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Empresa.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Empresa empresa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(empresa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Empresa empresa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(empresa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Empresa empresa) {
        if (pode_remover(empresa)) {
            try {
                entityManager.getTransaction().begin();
                empresa = entityManager.find(Empresa.class, empresa.getId());
                entityManager.remove(empresa);
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
            Empresa empresa = getById(id);
            remove(empresa);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Empresa e) {
        int i = 0;
        List<Cidade> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cidade.class.getName()+" WHERE empresa_id =" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }

}
