/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
public class Execucao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idExecucao;
    @Column
    private Integer nr_triagem;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_inicioexecucao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_fimexecucao;

    public Execucao() {
    }

    public Long getId() {
        return idExecucao;
    }

    public void setId(Long id) {
        this.idExecucao = id;
    }

    public Integer getNr_triagem() {
        return nr_triagem;
    }

    public void setNr_triagem(Integer nr_triagem) {
        this.nr_triagem = nr_triagem;
    }

    public Date getDh_inicioexecucao() {
        return dh_inicioexecucao;
    }

    public void setDh_inicioexecucao(Date dh_inicioexecucao) {
        this.dh_inicioexecucao = dh_inicioexecucao;
    }

    public Date getDh_fimexecucao() {
        return dh_fimexecucao;
    }

    public void setDh_fimexecucao(Date dh_fimexecucao) {
        this.dh_fimexecucao = dh_fimexecucao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.idExecucao != null ? this.idExecucao.hashCode() : 0);
        hash = 97 * hash + (this.nr_triagem != null ? this.nr_triagem.hashCode() : 0);
        hash = 97 * hash + (this.dh_inicioexecucao != null ? this.dh_inicioexecucao.hashCode() : 0);
        hash = 97 * hash + (this.dh_fimexecucao != null ? this.dh_fimexecucao.hashCode() : 0);
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
        final Execucao other = (Execucao) obj;
        if (this.idExecucao != other.idExecucao && (this.idExecucao == null || !this.idExecucao.equals(other.idExecucao))) {
            return false;
        }
        if (this.nr_triagem != other.nr_triagem && (this.nr_triagem == null || !this.nr_triagem.equals(other.nr_triagem))) {
            return false;
        }
        if (this.dh_inicioexecucao != other.dh_inicioexecucao && (this.dh_inicioexecucao == null || !this.dh_inicioexecucao.equals(other.dh_inicioexecucao))) {
            return false;
        }
        if (this.dh_fimexecucao != other.dh_fimexecucao && (this.dh_fimexecucao == null || !this.dh_fimexecucao.equals(other.dh_fimexecucao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idExecucao.toString();
    }

}
