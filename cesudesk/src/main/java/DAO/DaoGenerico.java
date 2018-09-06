/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import javax.persistence.EntityManager;

public abstract class DaoGenerico<T> {

    private Class<T> entityClass;

    public DaoGenerico(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T getById(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public void save(T entity) {
        getEntityManager().merge(entity);
    }

    public void delete(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public List<T> findAll() {
        return getEntityManager().createQuery(("FROM " + entityClass.getName()))
                .getResultList();
    }
}
