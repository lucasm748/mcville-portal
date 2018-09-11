/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author MC Ville
 */
@Entity
public class Restricao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String descricao;
    @Column
    private String tp_restricao;
    @Column
    private String tp_devedor;
    @Column
    private String ds_observacao;
    @Column
    private BigDecimal vl_restricao;
    @Temporal(TemporalType.DATE)
    private Date dt_inscricao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt_consulta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTp_restricao() {
        return tp_restricao;
    }

    public void setTp_restricao(String tp_restricao) {
        this.tp_restricao = tp_restricao;
    }

    public String getTp_devedor() {
        return tp_devedor;
    }

    public void setTp_devedor(String tp_devedor) {
        this.tp_devedor = tp_devedor;
    }

    public String getDs_observacao() {
        return ds_observacao;
    }

    public void setDs_observacao(String ds_observacao) {
        this.ds_observacao = ds_observacao;
    }

    public BigDecimal getVl_restricao() {
        return vl_restricao;
    }

    public void setVl_restricao(BigDecimal vl_restricao) {
        this.vl_restricao = vl_restricao;
    }

    public Date getDt_inscricao() {
        return dt_inscricao;
    }

    public void setDt_inscricao(Date dt_inscricao) {
        this.dt_inscricao = dt_inscricao;
    }

    public Date getDt_consulta() {
        return dt_consulta;
    }

    public void setDt_consulta(Date dt_consulta) {
        this.dt_consulta = dt_consulta;
    }
    
    
}
