/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;
import modelo.Empresa;

/**
 *
 * @author jaime
 */
public class EmpresaControle {

    private DaoGenerico dao;
    private static EmpresaControle instance;

    public EmpresaControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized EmpresaControle getInstance() {
        if (instance == null) {
            instance = new EmpresaControle();
        }
        return instance;
    }

    public void salvar(Empresa obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<Empresa> listaTodos() {
        return dao.list(Empresa.class);
    }

    public void delete(Empresa obj) {
        dao.delete(obj);
    }

    public List<Empresa> listaFiltrando(String filtro, int tipo) {
        if (tipo == 0) {
            return dao.listCriterio(Empresa.class, "nome", filtro);

           } else {
            return dao.listCriterio(Empresa.class, "cnpj", filtro);
        }



    }
     public boolean listaSenha(String nome, String senha) {  
         return dao.listaSenha(nome, senha);
                  }  
}
