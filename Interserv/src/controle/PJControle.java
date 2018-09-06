/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;

import modelo.Pessoa;
import modelo.PessoaJ;

/**
 *
 * @author jaime
 */
public class PJControle {

    private DaoGenerico dao;
    private static PJControle instance;

    public PJControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized PJControle getInstance() {
        if (instance == null) {
            instance = new PJControle();
        }
        return instance;
    }

    public void salvar(PessoaJ obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<PessoaJ> listaTodos() {
        return dao.list(PessoaJ.class);
    }

    public void delete(PessoaJ Cid) {
        dao.delete(Cid);
    }

    public List<PessoaJ> listaFiltrando(String filtro, int tipo) {
        if (tipo == 0) {
            return dao.listCriterio(PessoaJ.class, "inscricao", filtro);
        } else {
            return dao.listCriterio(PessoaJ.class, "cnpj", filtro);

        }
    }
    
}
