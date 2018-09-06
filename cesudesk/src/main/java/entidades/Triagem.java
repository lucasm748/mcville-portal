/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author lucas.andrade
 */
@Entity
public class Triagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTriagem;
    @ManyToOne()
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;
    @Column
    private String desc_triagem;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_inicio_triagem;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_fim_triagem;
    @Column
    private String inf_resultadotriagem;
    @Column
    private String qt_horas_triagem;
    @Column
    private String tp_statustriagem;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Execucao> execucoes;

    public Triagem() {
        this.execucoes = new ArrayList<Execucao>();
        this.qt_horas_triagem = "0";
        this.tp_statustriagem = "Aberta";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Long getIdTriagem() {
        return idTriagem;
    }

    public void setIdTriagem(Long idTriagem) {
        this.idTriagem = idTriagem;
    }

    public String getDesc_triagem() {
        return desc_triagem;
    }

    public void setDesc_triagem(String desc_triagem) {
        this.desc_triagem = desc_triagem;
    }

    public Date getDh_inicio_triagem() {
        return dh_inicio_triagem;
    }

    public void setDh_inicio_triagem(Date dh_inicio_triagem) {
        this.dh_inicio_triagem = dh_inicio_triagem;
    }

    public Date getDh_fim_triagem() {
        return dh_fim_triagem;
    }

    public void setDh_fim_triagem(Date dh_fim_triagem) {
        this.dh_fim_triagem = dh_fim_triagem;
    }

    public String getInf_resultadotriagem() {
        return inf_resultadotriagem;
    }

    public void setInf_resultadotriagem(String inf_resultadotriagem) {
        this.inf_resultadotriagem = inf_resultadotriagem;
    }

    public String getQt_horas_triagem() {
        return qt_horas_triagem;
    }

    public void setQt_horas_triagem(String qt_horas_triagem) {
        this.qt_horas_triagem = qt_horas_triagem;
    }

    public String getTp_statustriagem() {
        return tp_statustriagem;
    }

    public void setTp_statustriagem(String tp_statustriagem) {
        this.tp_statustriagem = tp_statustriagem;
    }

    public List<Execucao> getExecucoes() {
        return execucoes;
    }

    public void setExecucoes(List<Execucao> execucoes) {
        this.execucoes = execucoes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.idTriagem != null ? this.idTriagem.hashCode() : 0);
        hash = 47 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 47 * hash + (this.desc_triagem != null ? this.desc_triagem.hashCode() : 0);
        hash = 47 * hash + (this.dh_inicio_triagem != null ? this.dh_inicio_triagem.hashCode() : 0);
        hash = 47 * hash + (this.dh_fim_triagem != null ? this.dh_fim_triagem.hashCode() : 0);
        hash = 47 * hash + (this.inf_resultadotriagem != null ? this.inf_resultadotriagem.hashCode() : 0);
        hash = 47 * hash + (this.qt_horas_triagem != null ? this.qt_horas_triagem.hashCode() : 0);
        hash = 47 * hash + (this.tp_statustriagem != null ? this.tp_statustriagem.hashCode() : 0);
        hash = 47 * hash + (this.execucoes != null ? this.execucoes.hashCode() : 0);
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
        final Triagem other = (Triagem) obj;
        if ((this.desc_triagem == null) ? (other.desc_triagem != null) : !this.desc_triagem.equals(other.desc_triagem)) {
            return false;
        }
        if ((this.inf_resultadotriagem == null) ? (other.inf_resultadotriagem != null) : !this.inf_resultadotriagem.equals(other.inf_resultadotriagem)) {
            return false;
        }
        if ((this.qt_horas_triagem == null) ? (other.qt_horas_triagem != null) : !this.qt_horas_triagem.equals(other.qt_horas_triagem)) {
            return false;
        }
        if ((this.tp_statustriagem == null) ? (other.tp_statustriagem != null) : !this.tp_statustriagem.equals(other.tp_statustriagem)) {
            return false;
        }
        if (this.idTriagem != other.idTriagem && (this.idTriagem == null || !this.idTriagem.equals(other.idTriagem))) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        if (this.dh_inicio_triagem != other.dh_inicio_triagem && (this.dh_inicio_triagem == null || !this.dh_inicio_triagem.equals(other.dh_inicio_triagem))) {
            return false;
        }
        if (this.dh_fim_triagem != other.dh_fim_triagem && (this.dh_fim_triagem == null || !this.dh_fim_triagem.equals(other.dh_fim_triagem))) {
            return false;
        }
        if (this.execucoes != other.execucoes && (this.execucoes == null || !this.execucoes.equals(other.execucoes))) {
            return false;
        }
        return true;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return idTriagem.toString();
    }

}
