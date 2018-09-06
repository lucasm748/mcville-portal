/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidades.Projeto;
import DAO.ProjetoDAO;
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
@ManagedBean(name = "ProjetoMB")
@SessionScoped
public class ProjetoControle implements Serializable {
    private Projeto projeto = new Projeto();
    @EJB
    private ProjetoDAO projetoDAO;
    private Converter projetoConverter;
    private List<Projeto> lista = new ArrayList<Projeto>();



    public String salvar() {
        projetoDAO.save(projeto);
        return "/aplicacao/projeto/lista_projeto.xhtml";
    }

    public void novo() {
        projeto = new Projeto();

    }

    public void editar(ActionEvent e) {
        projeto = (Projeto) e.getComponent().getAttributes().get("projeto");
    }

    public void excluir(ActionEvent e) {
        projeto = (Projeto) e.getComponent().getAttributes().get("projeto");
        projetoDAO.delete(projeto);
    }

    public void cancelar() {
        projeto = new Projeto();
    }

    public List<Projeto> getLista() {
        return projetoDAO.findAll();
    }

    public void setLista(List<Projeto> lista) {
        this.lista = lista;
    }

    public ProjetoDAO getProjetoDAO() {
        return projetoDAO;
    }

    public void setProjetoDAO(ProjetoDAO projetoDAO) {
        this.projetoDAO = projetoDAO;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Converter getProjetoConverter() {
        return projetoConverter;
    }

    public void setProjetoConverter(Converter projetoConverter) {
        this.projetoConverter = projetoConverter;
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
        final ProjetoControle other = (ProjetoControle) obj;
        return Objects.equals(this.lista, other.lista);
    }

//    @PostConstruct
//    public void init() {
//        if (this.projetoDAO.findAll() != null) {
//            this.lista = this.projetoDAO.findAll();
//        }
//    }
    
}
