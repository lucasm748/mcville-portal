/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;
import modelo.Usuario;

/**
 *
 * @author jaime
 */
public class UsuarioControle {

    private DaoGenerico dao;
    private static UsuarioControle instance;

    public UsuarioControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized UsuarioControle getInstance() {
        if (instance == null) {
            instance = new UsuarioControle();
        }
        return instance;
    }

    public void salvar(Usuario obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<Usuario> listaTodos() {
        return dao.list(Usuario.class);
    }

    public void delete(Usuario Pes) {
        dao.delete(Pes);
    }

    public List<Usuario> listaFiltrando(String filtro, int tipo) {
        if (tipo == 0) {
            return dao.listCriterio(Usuario.class, "nome", filtro);

        } else if (tipo == 1) {
            return dao.listCriterio(Usuario.class, "senha", filtro);
        } else {
            return dao.listCriterio(Usuario.class, "cpf", filtro);
        }



    }
     public boolean listaSenha(String nome, String senha) {  
         return dao.listaSenha(nome, senha);
                  }  
}
