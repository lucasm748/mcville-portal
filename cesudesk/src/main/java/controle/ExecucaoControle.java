/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.CargoDAO;
import DAO.ExecucaoDAO;
import entidades.Cargo;
import entidades.Execucao;
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
@ManagedBean(name = "ExecucaoMB")
@SessionScoped
public class ExecucaoControle implements Serializable {

    private Execucao execucao = new Execucao();
    @EJB
    private ExecucaoDAO execucaoDAO;
    @EJB
    private CargoDAO cargoDAO;
    
    private Converter cargoConverter;
    private List<Execucao> lista = new ArrayList<Execucao>();
   
    public String salvar() {
        execucaoDAO.save(execucao);
        return "/aplicacao/execucao/lista_execucao";
    }

    public void novo() {
        execucao = new Execucao();
    }

    public Converter getCargoConverter() {
        if (cargoConverter == null) {
            cargoConverter = new ConverterGenerico(cargoDAO);
        }
        return cargoConverter;
    }

    public void cancelar() {
        execucao = new Execucao();
    }

    public void setCargoConverter(Converter cargoConverter) {
        this.cargoConverter = cargoConverter;
    }

    public void editar(ActionEvent e) {
        execucao = (Execucao) e.getComponent().getAttributes().get("execucao");
    }

    public void excluir(ActionEvent e) {
        execucao = (Execucao) e.getComponent().getAttributes().get("execucao");
        execucaoDAO.delete(execucao);
    }

    public List<Execucao> getLista() {
        return execucaoDAO.findAll();
    }

    public void setLista(List<Execucao> lista) {
        this.lista = lista;
    }

    public ExecucaoDAO getExecucaoDAO() {
        return execucaoDAO;
    }

    public void setExecucaoDAO(ExecucaoDAO execucaoDAO) {
        this.execucaoDAO = execucaoDAO;
    }

    public Execucao getExecucao() {
        return execucao;
    }

    public void setExecucao(Execucao execucao) {
        this.execucao = execucao;
    }

    public List<SelectItem> getCargos() {
        List<SelectItem> listaCargos = new ArrayList<SelectItem>();
        for (Cargo e : cargoDAO.findAll()) {
            listaCargos.add(new SelectItem(e, e.getDesc_cargo()));
        }
        return listaCargos;
    }


}
