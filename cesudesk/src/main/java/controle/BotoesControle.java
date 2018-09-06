/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.UsuarioDAO;
import entidades.Triagem;
import entidades.Usuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author lucas
 */
@ManagedBean(name = "botoesMB")
@SessionScoped
public class BotoesControle implements Serializable {

    private String btIniciarAgora;
    private String btIniciarApos;
    private String btPausar;
    private String btEncerrar;
    private Triagem itemselecionado;
    @EJB
    private UsuarioDAO usuarioDAO;

    public void validaBotoes() {
        if (itemselecionado.getUsuario().getCd_usuario().equals(getUsuarioSsesao().getCd_usuario())) {
            btEncerrar = "true";
            btIniciarAgora = "true";
            btIniciarApos = "true";
            btPausar = "true";
            System.out.println("entrou no if do usuariotriagem = sessao");
        } else {
            btEncerrar = "false";
            btIniciarAgora = "false";
            btIniciarApos = "false";
            btPausar = "false";
            System.out.println("entrou no else do usuariotriagem = sessao");
        }
    }

    public Usuario getUsuarioSsesao() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(login);
        Usuario user = usuarioDAO.pesquisa(login);
        return user;
    }

    public void onRowSelect(SelectEvent event) {
        itemselecionado = new Triagem();
        itemselecionado = (Triagem) event.getObject();
//        FacesMessage msg = new FacesMessage("Adicionado: ".concat(itemselecionado.getIdTriagem().toString()));
//        FacesContext.getCurrentInstance().addMessage(null,msg);
        validaBotoes();
    }

    public void onRowUnselect(UnselectEvent event) {
        itemselecionado = new Triagem();
        validaBotoes();
        Usuario usu = getUsuarioSsesao();
    }

    public void handleSelect(SelectEvent event) {
        System.out.println(event.getObject().getClass().toString());
        Triagem t = (Triagem) event.getObject();
        this.itemselecionado = t;
        validaBotoes();
    }

    public String getBtIniciarAgora() {
        return btIniciarAgora;
    }

    public void setBtIniciarAgora(String btIniciarAgora) {
        this.btIniciarAgora = btIniciarAgora;
    }

    public String getBtIniciarApos() {
        return btIniciarApos;
    }

    public void setBtIniciarApos(String btIniciarApos) {
        this.btIniciarApos = btIniciarApos;
    }

    public String getBtPausar() {
        return btPausar;
    }

    public void setBtPausar(String btPausar) {
        this.btPausar = btPausar;
    }

    public String getBtEncerrar() {
        return btEncerrar;
    }

    public void setBtEncerrar(String btEncerrar) {
        this.btEncerrar = btEncerrar;
    }

    public Triagem getItemselecionado() {
        return itemselecionado;
    }

    public void setItemselecionado(Triagem itemselecionado) {
        this.itemselecionado = itemselecionado;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

}
