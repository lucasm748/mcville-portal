/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidades.Cargo;
import DAO.CargoDAO;
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
@ManagedBean(name = "CargoMB")
@SessionScoped
public class CargoControle implements Serializable {
    private Cargo cargo = new Cargo();
    @EJB
    private CargoDAO cargoDAO;
    private Converter cargoConverter;
    private List<Cargo> lista = new ArrayList<Cargo>();



    public String salvar() {
        cargoDAO.save(cargo);
        return "/aplicacao/usuario/cargo/lista_cargo.xhtml";
    }

    public void novo() {
        cargo = new Cargo();

    }

    public void editar(ActionEvent e) {
        cargo = (Cargo) e.getComponent().getAttributes().get("cargo");
    }

    public void excluir(ActionEvent e) {
        cargo = (Cargo) e.getComponent().getAttributes().get("cargo");
        cargoDAO.delete(cargo);
    }

    public void cancelar() {
        cargo = new Cargo();
    }

    public List<Cargo> getLista() {
        return cargoDAO.findAll();
    }

    public void setLista(List<Cargo> lista) {
        this.lista = lista;
    }

    public CargoDAO getCargoDAO() {
        return cargoDAO;
    }

    public void setCargoDAO(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Converter getCargoConverter() {
        return cargoConverter;
    }

    public void setCargoConverter(Converter cargoConverter) {
        this.cargoConverter = cargoConverter;
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
        final CargoControle other = (CargoControle) obj;
        return Objects.equals(this.lista, other.lista);
    }

//    @PostConstruct
//    public void init() {
//        if (this.cargoDAO.findAll() != null) {
//            this.lista = this.cargoDAO.findAll();
//        }
//    }
    
}
