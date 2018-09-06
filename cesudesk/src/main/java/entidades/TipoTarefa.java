/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author lucas.andrade
 */
@Entity
public class TipoTarefa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cd_tipotarefa;
    @Column
    private String desc_tipotarefa;

    public TipoTarefa() {
    }

    public Long getCd_tipotarefa() {
        return cd_tipotarefa;
    }

    public void setCd_tipotarefa(Long cd_tipotarefa) {
        this.cd_tipotarefa = cd_tipotarefa;
    }

    public String getDesc_tipotarefa() {
        return desc_tipotarefa;
    }

    public void setDesc_tipotarefa(String desc_tipotarefa) {
        this.desc_tipotarefa = desc_tipotarefa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.cd_tipotarefa);
        hash = 47 * hash + Objects.hashCode(this.desc_tipotarefa);
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
        final TipoTarefa other = (TipoTarefa) obj;
        if (!Objects.equals(this.desc_tipotarefa, other.desc_tipotarefa)) {
            return false;
        }
        if (!Objects.equals(this.cd_tipotarefa, other.cd_tipotarefa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cd_tipotarefa.toString();
    }

}
