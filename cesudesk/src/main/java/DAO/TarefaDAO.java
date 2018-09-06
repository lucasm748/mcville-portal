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
public class TarefaDAO extends DaoGenerico<Tarefa> {

    @PersistenceContext(unitName = "cesudeskpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarefaDAO() {
        super(Tarefa.class);
    }

    public List<Tarefa> listar_atribuidas(Usuario usu) {
        return em.createQuery("from Tarefa t inner join fetch t.triagens r inner join fetch r.usuario u where u.cd_usuario= '" + usu.getCd_usuario().toString() + "'").getResultList();
    }

    public List<Triagem> listar_triagens_usuario(Usuario usu) {
        return em.createQuery("from Triagem as t "
                + "join t.usuario as u "
                + "left join t.execucoes as e "
                + "where u.cd_usuario ='" + usu.getCd_usuario().toString() + "'").getResultList();

    }
}
