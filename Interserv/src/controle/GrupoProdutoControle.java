/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;
import modelo.GrupoProduto;

/**
 *
 * @author jaime
 */
public class GrupoProdutoControle {

    private DaoGenerico dao;
    private static GrupoProdutoControle instance;

    public GrupoProdutoControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized GrupoProdutoControle getInstance() {
        if (instance == null) {
            instance = new GrupoProdutoControle();
        }
        return instance;
    }

    public void salvar(GrupoProduto obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<GrupoProduto> listaTodos() {
        return dao.list(GrupoProduto.class);
    }

    public void delete(GrupoProduto est) {
        dao.delete(est);
    }

    public List<GrupoProduto> listaFiltrando(String filtro, int tipo) {
         return dao.listCriterio(GrupoProduto.class, "descricao", filtro);
       
    }
}
