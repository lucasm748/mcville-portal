/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;
import modelo.Produto;

/**
 *
 * @author jaime
 */
public class ProdutoControle {

    private DaoGenerico dao;
    private static ProdutoControle instance;

    public ProdutoControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized ProdutoControle getInstance() {
        if (instance == null) {
            instance = new ProdutoControle();
        }
        return instance;
    }

    public void salvar(Produto obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<Produto> listaTodos() {
        return dao.list(Produto.class);
    }

    public void delete(Produto prod) {
        dao.delete(prod);
    }

    public List<Produto> listaFiltrando(String filtro, int tipo) {
      return dao.listCriterio(Produto.class, "descricao", filtro);
        }
}
