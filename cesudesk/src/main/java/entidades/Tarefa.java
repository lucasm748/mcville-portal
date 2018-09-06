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

/**
 *
 * @author lucas.andrade
 */
@Entity
public class Tarefa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cd_tarefa;
    @Column
    private String desc_tarefa;
    @Column
    private Integer prioridade;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_cadastro;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_entrega_prev;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_fechament;
    @Column
    private String inf_complementar;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_TIPOTAREFA")
    private TipoTarefa tipotarefa;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_MODULO")
    private Modulo modulo;
    @Column
    private String tp_statustarefa;
    @ManyToOne
    private Usuario solicitante;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Triagem> triagens;
    @Column
    private String titulo;

    public Tarefa() {
        this.triagens = new ArrayList<Triagem>();
        this.dh_cadastro = new Date();
        this.tp_statustarefa = "Aberta";
    }

    public Long getCd_tarefa() {
        return cd_tarefa;
    }

    public void setCd_tarefa(Long cd_tarefa) {
        this.cd_tarefa = cd_tarefa;
    }

    public String getDesc_tarefa() {
        return desc_tarefa;
    }

    public void setDesc_tarefa(String desc_tarefa) {
        this.desc_tarefa = desc_tarefa;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Date getDh_cadastro() {
        return dh_cadastro;
    }

    public void setDh_cadastro(Date dh_cadastro) {
        this.dh_cadastro = dh_cadastro;
    }

    public Date getDh_entrega_prev() {
        return dh_entrega_prev;
    }

    public void setDh_entrega_prev(Date dh_entrega_prev) {
        this.dh_entrega_prev = dh_entrega_prev;
    }

    public Date getDh_fechament() {
        return dh_fechament;
    }

    public void setDh_fechament(Date dh_fechament) {
        this.dh_fechament = dh_fechament;
    }

    public String getInf_complementar() {
        return inf_complementar;
    }

    public void setInf_complementar(String inf_complementar) {
        this.inf_complementar = inf_complementar;
    }

    public TipoTarefa getTipotarefa() {
        return tipotarefa;
    }

    public void setTipotarefa(TipoTarefa tipotarefa) {
        this.tipotarefa = tipotarefa;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public String getTp_statustarefa() {
        return tp_statustarefa;
    }

    public void setTp_statustarefa(String tp_statustarefa) {
        this.tp_statustarefa = tp_statustarefa;
    }

    public List<Triagem> getTriagens() {
        return triagens;
    }

    public void setTriagens(List<Triagem> triagens) {
        this.triagens = triagens;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.cd_tarefa != null ? this.cd_tarefa.hashCode() : 0);
        hash = 89 * hash + (this.desc_tarefa != null ? this.desc_tarefa.hashCode() : 0);
        hash = 89 * hash + (this.prioridade != null ? this.prioridade.hashCode() : 0);
        hash = 89 * hash + (this.dh_cadastro != null ? this.dh_cadastro.hashCode() : 0);
        hash = 89 * hash + (this.dh_entrega_prev != null ? this.dh_entrega_prev.hashCode() : 0);
        hash = 89 * hash + (this.dh_fechament != null ? this.dh_fechament.hashCode() : 0);
        hash = 89 * hash + (this.inf_complementar != null ? this.inf_complementar.hashCode() : 0);
        hash = 89 * hash + (this.tipotarefa != null ? this.tipotarefa.hashCode() : 0);
        hash = 89 * hash + (this.modulo != null ? this.modulo.hashCode() : 0);
        hash = 89 * hash + (this.tp_statustarefa != null ? this.tp_statustarefa.hashCode() : 0);
        hash = 89 * hash + (this.solicitante != null ? this.solicitante.hashCode() : 0);
        hash = 89 * hash + (this.triagens != null ? this.triagens.hashCode() : 0);
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
        final Tarefa other = (Tarefa) obj;
        if ((this.desc_tarefa == null) ? (other.desc_tarefa != null) : !this.desc_tarefa.equals(other.desc_tarefa)) {
            return false;
        }
        if ((this.inf_complementar == null) ? (other.inf_complementar != null) : !this.inf_complementar.equals(other.inf_complementar)) {
            return false;
        }
        if ((this.tp_statustarefa == null) ? (other.tp_statustarefa != null) : !this.tp_statustarefa.equals(other.tp_statustarefa)) {
            return false;
        }
        if (this.cd_tarefa != other.cd_tarefa && (this.cd_tarefa == null || !this.cd_tarefa.equals(other.cd_tarefa))) {
            return false;
        }
        if (this.prioridade != other.prioridade && (this.prioridade == null || !this.prioridade.equals(other.prioridade))) {
            return false;
        }
        if (this.dh_cadastro != other.dh_cadastro && (this.dh_cadastro == null || !this.dh_cadastro.equals(other.dh_cadastro))) {
            return false;
        }
        if (this.dh_entrega_prev != other.dh_entrega_prev && (this.dh_entrega_prev == null || !this.dh_entrega_prev.equals(other.dh_entrega_prev))) {
            return false;
        }
        if (this.dh_fechament != other.dh_fechament && (this.dh_fechament == null || !this.dh_fechament.equals(other.dh_fechament))) {
            return false;
        }
        if (this.tipotarefa != other.tipotarefa && (this.tipotarefa == null || !this.tipotarefa.equals(other.tipotarefa))) {
            return false;
        }
        if (this.modulo != other.modulo && (this.modulo == null || !this.modulo.equals(other.modulo))) {
            return false;
        }
        if (this.solicitante != other.solicitante && (this.solicitante == null || !this.solicitante.equals(other.solicitante))) {
            return false;
        }
        if (this.triagens != other.triagens && (this.triagens == null || !this.triagens.equals(other.triagens))) {
            return false;
        }
        return true;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return cd_tarefa.toString();
    }

}
