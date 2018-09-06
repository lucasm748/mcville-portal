/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidades.Modulo;
import DAO.ModuloDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;

/**
 *
 * @author lucas
 */
@ManagedBean(name = "ModuloMB")
@SessionScoped
public class ModuloControle implements Serializable {
    private Modulo modulo = new Modulo();
    @EJB
    private ModuloDAO moduloDAO;
    private Converter moduloConverter;
    private List<Modulo> lista = new ArrayList<Modulo>();



    public String salvar() {
        moduloDAO.save(modulo);
        return "/aplicacao/modulo/lista_modulo.xhtml";
    }

    public void novo() {
        modulo = new Modulo();

    }

    public void editar(ActionEvent e) {
        modulo = (Modulo) e.getComponent().getAttributes().get("modulo");
    }

    public void excluir(ActionEvent e) {
        modulo = (Modulo) e.getComponent().getAttributes().get("modulo");
        moduloDAO.delete(modulo);
    }

    public void cancelar() {
        modulo = new Modulo();
    }

    public List<Modulo> getLista() {
        return moduloDAO.findAll();
    }

    public void setLista(List<Modulo> lista) {
        this.lista = lista;
    }

    public ModuloDAO getModuloDAO() {
        return moduloDAO;
    }

    public void setModuloDAO(ModuloDAO moduloDAO) {
        this.moduloDAO = moduloDAO;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Converter getModuloConverter() {
        return moduloConverter;
    }

    public void setModuloConverter(Converter moduloConverter) {
        this.moduloConverter = moduloConverter;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModuloControle other = (ModuloControle) obj;
        return Objects.equals(this.lista, other.lista);
    }

//    @PostConstruct
//    public void init() {
//        if (this.moduloDAO.findAll() != null) {
//            this.lista = this.moduloDAO.findAll();
//        }
//    }
    
}
