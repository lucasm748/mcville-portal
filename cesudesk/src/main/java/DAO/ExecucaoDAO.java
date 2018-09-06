/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entidades.Execucao;
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
public class ExecucaoDAO extends DaoGenerico<Execucao> {

    @PersistenceContext(unitName = "cesudeskpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExecucaoDAO() {
        super(Execucao.class);
    }

    public List<Execucao> buscaExecucoes(Triagem triagem) {
        return em.createQuery("FROM Triagem t inner join fetch t.usuario u "
                + "inner join fetch t.execucoes e where t.id= '"
                + triagem.getIdTriagem().toString() + "'").getResultList();
    }

    public Execucao getUltimaExecUsuario(Usuario usu) {
        return (Execucao) em.createQuery("Select max(execucao.dh_fimexecucao) From Execucao u "
                + "inner join u.usuario where u.cd_usuario= '"
                + usu.getCd_usuario().toString() + "'").getSingleResult();
    }

    public Execucao getExecucaoAberta(Triagem itemSelecionado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
