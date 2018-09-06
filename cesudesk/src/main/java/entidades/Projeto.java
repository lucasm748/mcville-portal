/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucas.andrade
 */
@Entity
public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cd_projeto;
    @Column
    private String desc_projeto;
    @Temporal(TemporalType.DATE)
    private Date dt_inicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_fechamento;
    @Column
    private String inf_complementar;
    @Column
    private String tp_statusprojeto;

    public Long getCd_projeto() {
        return cd_projeto;
    }

    public void setCd_projeto(Long cd_projeto) {
        this.cd_projeto = cd_projeto;
    }

    public String getDesc_projeto() {
        return desc_projeto;
    }

    public void setDesc_projeto(String desc_projeto) {
        this.desc_projeto = desc_projeto;
    }

    public Date getDt_inicio() {
        return dt_inicio;
    }

    public void setDt_inicio(Date dt_inicio) {
        this.dt_inicio = dt_inicio;
    }

    public Date getDh_fechamento() {
        return dh_fechamento;
    }

    public void setDh_fechamento(Date dh_fechamento) {
        this.dh_fechamento = dh_fechamento;
    }

    public String getInf_complementar() {
        return inf_complementar;
    }

    public void setInf_complementar(String inf_complementar) {
        this.inf_complementar = inf_complementar;
    }

    public String getTp_statusprojeto() {
        return tp_statusprojeto;
    }

    public void setTp_statusprojeto(String tp_statusprojeto) {
        this.tp_statusprojeto = tp_statusprojeto;
    }

    public Projeto() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.cd_projeto);
        hash = 17 * hash + Objects.hashCode(this.desc_projeto);
        hash = 17 * hash + Objects.hashCode(this.dt_inicio);
        hash = 17 * hash + Objects.hashCode(this.dh_fechamento);
        hash = 17 * hash + Objects.hashCode(this.inf_complementar);
        hash = 17 * hash + Objects.hashCode(this.tp_statusprojeto);
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
        final Projeto other = (Projeto) obj;
        if (!Objects.equals(this.desc_projeto, other.desc_projeto)) {
            return false;
        }
        if (!Objects.equals(this.inf_complementar, other.inf_complementar)) {
            return false;
        }
        if (!Objects.equals(this.tp_statusprojeto, other.tp_statusprojeto)) {
            return false;
        }
        if (!Objects.equals(this.cd_projeto, other.cd_projeto)) {
            return false;
        }
        if (!Objects.equals(this.dt_inicio, other.dt_inicio)) {
            return false;
        }
        if (!Objects.equals(this.dh_fechamento, other.dh_fechamento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    return cd_projeto.toString();
    }

}
