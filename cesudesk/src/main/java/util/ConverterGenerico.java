/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;


import DAO.DaoGenerico;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Lucas
 */
public class ConverterGenerico implements Converter, Serializable {

    private DaoGenerico dao;

    public ConverterGenerico(DaoGenerico dao) {
        this.dao = dao;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
      
        return dao.getById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
