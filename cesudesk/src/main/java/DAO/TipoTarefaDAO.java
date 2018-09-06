/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entidades.TipoTarefa;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lucas
 */
@Stateless
public class TipoTarefaDAO extends DaoGenerico<TipoTarefa> {

    
    @PersistenceContext(unitName = "cesudeskpu")
        private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoTarefaDAO() {
        super(TipoTarefa.class);
    }
}
