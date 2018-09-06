/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidades.TipoTarefa;
import DAO.TipoTarefaDAO;
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
@ManagedBean(name = "TipoTarefaMB")
@SessionScoped
public class TipoTarefaControle implements Serializable {
    private TipoTarefa tipoTarefa = new TipoTarefa();
    @EJB
    private TipoTarefaDAO tipoTarefaDAO;
    private Converter tipoTarefaConverter;
    private List<TipoTarefa> lista = new ArrayList<TipoTarefa>();



    public String salvar() {
        tipoTarefaDAO.save(tipoTarefa);
        return "/aplicacao/tipo_tarefa/lista_tipotarefa.xhtml";
    }

    public void novo() {
        tipoTarefa = new TipoTarefa();

    }

    public void editar(ActionEvent e) {
        tipoTarefa = (TipoTarefa) e.getComponent().getAttributes().get("tipoTarefa");
    }

    public void excluir(ActionEvent e) {
        tipoTarefa = (TipoTarefa) e.getComponent().getAttributes().get("tipoTarefa");
        tipoTarefaDAO.delete(tipoTarefa);
    }

    public void cancelar() {
        tipoTarefa = new TipoTarefa();
    }

    public List<TipoTarefa> getLista() {
        return tipoTarefaDAO.findAll();
    }

    public void setLista(List<TipoTarefa> lista) {
        this.lista = lista;
    }

    public TipoTarefaDAO getTipoTarefaDAO() {
        return tipoTarefaDAO;
    }

    public void setTipoTarefaDAO(TipoTarefaDAO tipoTarefaDAO) {
        this.tipoTarefaDAO = tipoTarefaDAO;
    }

    public TipoTarefa getTipoTarefa() {
        return tipoTarefa;
    }

    public void setTipoTarefa(TipoTarefa tipoTarefa) {
        this.tipoTarefa = tipoTarefa;
    }

    public Converter getTipoTarefaConverter() {
        return tipoTarefaConverter;
    }

    public void setTipoTarefaConverter(Converter tipoTarefaConverter) {
        this.tipoTarefaConverter = tipoTarefaConverter;
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
        final TipoTarefaControle other = (TipoTarefaControle) obj;
        return Objects.equals(this.lista, other.lista);
    }

//    @PostConstruct
//    public void init() {
//        if (this.tipoTarefaDAO.findAll() != null) {
//            this.lista = this.tipoTarefaDAO.findAll();
//        }
//    }
    
}
