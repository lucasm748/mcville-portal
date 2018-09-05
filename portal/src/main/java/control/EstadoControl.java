/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import entity.Estado;
import java.util.List;


/**
 *
 * @author MC Ville
 */
public class EstadoControl {

    private DaoGenerico dao;
    private static EstadoControl instance;

    public EstadoControl() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized EstadoControl getInstance() {
        if (instance == null) {
            instance = new EstadoControl();
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
