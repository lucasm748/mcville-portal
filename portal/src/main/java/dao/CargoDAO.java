/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cargo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.criterion.Projections;

public class CargoDAO {

    private static CargoDAO instance;
    protected EntityManager entityManager;

    public static CargoDAO getInstance() {
        if (instance == null) {
            instance = new CargoDAO();
        }

        return instance;
    }

    public CargoDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Cargo getById(final int id) {
        return entityManager.find(Cargo.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Cargo> findAll() {
        return entityManager.createQuery("FROM " + Cargo.class.getName()).getResultList();
    }

    public List<Cargo> lista_filtrando(String filtro, String busca) {
        return entityManager.createQuery("FROM " + Cargo.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
    }

    public void persist(Cargo cargo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Cargo cargo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public int remove(Cargo cargo) {
        if (pode_remover(cargo)) {
            try {
                entityManager.getTransaction().begin();
                cargo = entityManager.find(Cargo.class, cargo.getId());
                entityManager.remove(cargo);
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
            Cargo cargo = getById(id);
            remove(cargo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean pode_remover(Cargo e) {
        int i = 0;
        List<Cargo> cidades = new ArrayList();
        cidades = entityManager.createQuery("FROM "+Cargo.class.getName()+" WHERE cargo_id =" + e.getId()).getResultList();
        i = cidades.size();
        if (i > 0) {
            return false;
        } else {
            return true;
        }

    }

}
