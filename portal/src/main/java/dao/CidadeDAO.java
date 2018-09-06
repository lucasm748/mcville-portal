/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Cidade;
import java.util.List;
  
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
  
public class CidadeDAO {
  
         private static CidadeDAO instance;
         protected EntityManager entityManager;
          
         public static CidadeDAO getInstance(){
                   if (instance == null){
                            instance = new CidadeDAO();
                   }
                    
                   return instance;
         }
  
         public CidadeDAO() {
                   entityManager = getEntityManager();
         }
  
         private EntityManager getEntityManager() {
                   EntityManagerFactory factory = Persistence.createEntityManagerFactory("br.com.mcvcille_portal_PU");
                   if (entityManager == null) {
                            entityManager = factory.createEntityManager();
                   }
  
                   return entityManager;
         }
  
         public Cidade getById(final int id) {
                   return entityManager.find(Cidade.class, id);
         }
  
         @SuppressWarnings("unchecked")
         public List<Cidade> findAll() {
                   return entityManager.createQuery("FROM " + Cidade.class.getName()).getResultList();
         }
         public List<Cidade> lista_filtrando(String filtro, String busca){
             return entityManager.createQuery("FROM " + Cidade.class.getName() + " WHERE UPPER(CAST(" + filtro + " as string)) like '%" + busca.toUpperCase() + "%'").getResultList();
         }
         public void persist(Cidade cliente) {
                   try {
                            entityManager.getTransaction().begin();
                            entityManager.persist(cliente);
                            entityManager.getTransaction().commit();
                   } catch (Exception ex) {
                            ex.printStackTrace();
                            entityManager.getTransaction().rollback();
                   }
         }
  
         public void merge(Cidade cliente) {
                   try {
                            entityManager.getTransaction().begin();
                            entityManager.merge(cliente);
                            entityManager.getTransaction().commit();
                   } catch (Exception ex) {
                            ex.printStackTrace();
                            entityManager.getTransaction().rollback();
                   }
         }
  
         public void remove(Cidade cliente) {
                   try {
                            entityManager.getTransaction().begin();
                            cliente = entityManager.find(Cidade.class, cliente.getId());
                            entityManager.remove(cliente);
                            entityManager.getTransaction().commit();
                   } catch (Exception ex) {
                            ex.printStackTrace();
                            entityManager.getTransaction().rollback();
                   }
         }
  
         public void removeById(final int id) {
                   try {
                            Cidade cliente = getById(id);
                            remove(cliente);
                   } catch (Exception ex) {
                            ex.printStackTrace();
                   }
         }
  
}