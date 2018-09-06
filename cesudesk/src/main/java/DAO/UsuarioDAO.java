/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entidades.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lucas
 */
@Stateless
public class UsuarioDAO extends DaoGenerico<Usuario> {

    @PersistenceContext(unitName = "cesudeskpu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario logar(String login, String senha) {
        Usuario usuario = new Usuario();
        try {
            usuario = (Usuario) em.createQuery(""
                    + "FROM Usuario AS c "
                    + "WHERE c.ativo='TRUE' "
                    + "AND c.nm_usuario='" + login + "' "
                    + "AND c.senha_usuario=('" + senha + "')").getSingleResult();
        } catch (Exception e) {
            System.out.println("DAO.UsuarioDAO.logar()" + e);
        }
        return usuario;
    }

    public Usuario pesquisa(String nm_usuario) {
        Usuario usuario = new Usuario();
        usuario = (Usuario) em.createQuery("FROM Usuario u where u.nm_usuario = '" + nm_usuario+"'").getSingleResult();
        return usuario;
    }
}
