/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author MC Ville
 */
public interface DaoGenerico {
    public Object save(Object objeto);
 
    public void delete(Object objeto);
 
    public List list(Class clazz);
 
    public List listaID(Class clazz, String atributo, Long id);
     
    public List listCriterio(Class clazz, String atributo, String criterio);
 
//    public boolean listaSenha(String nome, String senha);
 
    public Object getById(Serializable id, Class clazz);
 
//    public List listaData(Class clazz, String atributo, Date dataVenda);
     
//    public List listaSQL(Long id);
    
}
