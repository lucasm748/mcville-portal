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
public class Modulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cd_modulo;
    @Column
    private String desc_modulo;
    @Column
    private String inf_complementar;

    public Modulo() {
    }

    public Long getCd_modulo() {
        return cd_modulo;
    }

    public void setCd_modulo(Long cd_modulo) {
        this.cd_modulo = cd_modulo;
    }

    public String getDesc_modulo() {
        return desc_modulo;
    }

    public void setDesc_modulo(String desc_modulo) {
        this.desc_modulo = desc_modulo;
    }

    public String getInf_complementar() {
        return inf_complementar;
    }

    public void setInf_complementar(String inf_complementar) {
        this.inf_complementar = inf_complementar;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.cd_modulo);
        hash = 67 * hash + Objects.hashCode(this.desc_modulo);
        hash = 67 * hash + Objects.hashCode(this.inf_complementar);
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
        final Modulo other = (Modulo) obj;
        if (!Objects.equals(this.desc_modulo, other.desc_modulo)) {
            return false;
        }
        if (!Objects.equals(this.inf_complementar, other.inf_complementar)) {
            return false;
        }
        if (!Objects.equals(this.cd_modulo, other.cd_modulo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cd_modulo.toString();
    }

}
