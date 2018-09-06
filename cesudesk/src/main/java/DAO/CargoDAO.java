/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entidades.Cargo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lucas
 */
@Stateless
public class CargoDAO extends DaoGenerico<Cargo> {

    
    @PersistenceContext(unitName = "cesudeskpu")
        private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CargoDAO() {
        super(Cargo.class);
    }
}
