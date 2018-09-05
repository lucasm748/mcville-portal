/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import br.com.mcvcille.portal.entity.control.entity.Cidade;
import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;

/**
 *
 * @author MC Ville
 */
public class CidadeControl {

    private DaoGenerico dao;
    private static CidadeControl instance;

    public CidadeControl() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized CidadeControl getInstance() {
        if (instance == null) {
            instance = new CidadeControl();
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
