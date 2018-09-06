/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;
import modelo.Estado;

/**
 *
 * @author jaime
 */
public class EstadoControle {

    private DaoGenerico dao;
    private static EstadoControle instance;

    public EstadoControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized EstadoControle getInstance() {
        if (instance == null) {
            instance = new EstadoControle();
        }
        return instance;
    }

    public void salvar(Estado obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<Estado> listaTodos() {
        return dao.list(Estado.class);
    }

    public void delete(Estado est) {
        dao.delete(est);
    }

    public List<Estado> listaFiltrando(String filtro, int tipo) {

        if (tipo == 0) {
            return dao.listCriterio(Estado.class, "nome", filtro);
        } else {
            return dao.listCriterio(Estado.class, "sigla", filtro);


        }
    }
}
