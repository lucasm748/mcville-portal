/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entidades.Tarefa;
import entidades.Triagem;
import entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lucas
 */
@Stateless
public class TriagemDAO extends DaoGenerico<Triagem> {

    @PersistenceContext(unitName = "cesudeskpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TriagemDAO() {
        super(Triagem.class);
    }

    public Object listar_triagensTarefa(Tarefa tar) {
        return em.createQuery("from Tarefa a inner join fetch a.triagens as t "
                + "join t.usuario as u "
                + "left join t.execucoes as e "
                + "where  a.cd_tarefa= '" + tar.getCd_tarefa().toString() + "'").getSingleResult();
    }

    public Object listar_MinhasTriagensTarefa(Tarefa tar, Usuario u) {
        return em.createQuery("from Tarefa a inner join fetch a.triagens as t "
                + "join t.usuario as u "
                + "left join t.execucoes as e "
                + "where u.cd_usuario ='" + u.getCd_usuario().toString() + "' "
                + "and a.cd_tarefa= '" + tar.getCd_tarefa().toString() + "'").getSingleResult();
    }

}
