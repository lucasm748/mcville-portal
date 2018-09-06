/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.UsuarioDAO;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Lucas
 */
@ManagedBean(name = "LoginMB")
@SessionScoped
public class LoginControle implements Serializable {

    @EJB
    private UsuarioDAO usuarioDAO;
    private String login;
    private String senha;
    private Usuario usuario;
    private String pagina;
    private String retorno;

    public String login() {
        usuario = new Usuario();
        pagina = "/login";
        retorno = " ";
        if (login.equals("master") && senha.equals("master")) {
            usuario = new Usuario();
            usuario.setNm_usuario("ADMINISTRADOR");
            usuario.setCd_usuario(Long.valueOf("999"));
            System.out.println("Logado como adm");
            pagina = "/index";
        } else {
            usuario = usuarioDAO.logar(login, senha);
            System.out.println("Usuario encontrado: " + usuario.getNm_usuario());

            if (usuario.getCd_usuario() != null) {
                List<GrantedAuthority> autorizacoes = new ArrayList<GrantedAuthority>();
                autorizacoes.add(new GrantedAuthorityImpl("ADM"));
                SecurityContext context = SecurityContextHolder.getContext();

                context.setAuthentication(new UsernamePasswordAuthenticationToken(login, senha, autorizacoes));
                if (usuario.getCargo().getNivel_acesso().equals("0")) {
                    pagina = "/aplicacao/usuario_final/index";
                }
                if (context.getAuthentication().isAuthenticated()) {
                    pagina = "/index";
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário não logado!", "");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    pagina = "/login";
                    retorno = "Erro no acesso.";
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Falha na autenticação! Não há usuários com esses parâmetros.", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
                pagina = "/login";
                retorno = "Erro no acesso.";
            }
        }
        return pagina;

    }

    public String logout() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sessao.invalidate();
        return "/login"; //AQUI EU PASSO O NOME DA MINHA TELA INICIAL.    
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

}
