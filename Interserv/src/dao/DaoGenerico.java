package dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface DaoGenerico {

    public Object save(Object objeto);

    public void delete(Object objeto);

    public List list(Class clazz);

    public List listaID(Class clazz, String atributo, Long id);
    
    public List listCriterio(Class clazz, String atributo, String criterio);

    public boolean listaSenha(String nome, String senha);

    public Object getById(Serializable id, Class clazz);

    public List listaData(Class clazz, String atributo, Date dataVenda);
    
     public List listaSQL(Long id);
}
