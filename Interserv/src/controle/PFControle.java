/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;

import modelo.Pessoa;
import modelo.PessoaF;

/*mp*
 *
 * @author jaime
 */
public class PFControle {

    private DaoGenerico dao;
    private static PFControle instance;

    public PFControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized PFControle getInstance() {
        if (instance == null) {
            instance = new PFControle();
        }
        return instance;
    }

    public void salvar(PessoaF obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<PessoaF> listaTodos() {
        return dao.list(PessoaF.class);
    }

    public void delete(PessoaF Cid) {
        dao.delete(Cid);
    }

    public List<PessoaF> listaFiltrando(String filtro, int tipo) {
        if (tipo == 0) {
            return dao.listCriterio(PessoaF.class, "rg", filtro);
        } else {
            return dao.listCriterio(PessoaF.class, "cpf", filtro);

        }
    }
}
