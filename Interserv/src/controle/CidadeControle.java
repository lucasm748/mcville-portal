/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;
import modelo.Cidade;

/**
 *
 * @author jaime
 */ 
public class CidadeControle {

    private DaoGenerico dao;
    private static CidadeControle instance;

    public CidadeControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized CidadeControle getInstance() {
        if (instance == null) {
            instance = new CidadeControle();
        }
        return instance;
    }

    public void salvar(Cidade obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<Cidade> listaTodos() {
        return dao.list(Cidade.class);
    }

    public void delete(Cidade est) {
        dao.delete(est);
    }

    public List<Cidade> listaFiltrando(String filtro, int tipo) {
        if (tipo == 0) {
            return dao.listCriterio(Cidade.class, "nome", filtro);
        } else {
            return dao.listCriterio(Cidade.class, "estado", filtro);
        }
    }
}
