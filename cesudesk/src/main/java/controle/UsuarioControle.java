/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.CargoDAO;
import DAO.UsuarioDAO;
import entidades.Cargo;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import util.ConverterGenerico;

/**
 *
 * @author lucas
 */
@ManagedBean(name = "UsuarioMB")
@SessionScoped
public class UsuarioControle implements Serializable {

    private Usuario usuario = new Usuario();
    @EJB
    private UsuarioDAO usuarioDAO;
    @EJB
    private CargoDAO cargoDAO;
    
    private Converter cargoConverter;
    private List<Usuario> lista = new ArrayList<Usuario>();
   
    public String salvar() {
        usuarioDAO.save(usuario);
        return "/aplicacao/usuario/lista_usuario";
    }

    public void novo() {
        usuario = new Usuario();
    }

    public Converter getCargoConverter() {
        if (cargoConverter == null) {
            cargoConverter = new ConverterGenerico(cargoDAO);
        }
        return cargoConverter;
    }

    public void cancelar() {
        usuario = new Usuario();
    }

    public void setCargoConverter(Converter cargoConverter) {
        this.cargoConverter = cargoConverter;
    }

    public void editar(ActionEvent e) {
        usuario = (Usuario) e.getComponent().getAttributes().get("usuario");
    }

    public void excluir(ActionEvent e) {
        usuario = (Usuario) e.getComponent().getAttributes().get("usuario");
        usuarioDAO.delete(usuario);
    }

    public List<Usuario> getLista() {
        return usuarioDAO.findAll();
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<SelectItem> getCargos() {
        List<SelectItem> listaCargos = new ArrayList<SelectItem>();
        for (Cargo e : cargoDAO.findAll()) {
            listaCargos.add(new SelectItem(e, e.getDesc_cargo()));
        }
        return listaCargos;
    }


}
