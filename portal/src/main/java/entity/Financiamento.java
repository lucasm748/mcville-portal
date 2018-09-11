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
public class Financiamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String descricao;
    @Column
    private Double qt_parcelas;
    @Column
    private Double qt_parcelas_restantes;
    @Column
    private BigDecimal vl_financiado;
    @Column
    private BigDecimal vl_parcela;
    @Column
    private BigDecimal vl_devedor;
    @Temporal(TemporalType.DATE)
    private Date dt_quitacao_prev;
    @Column
    private String tp_objeto;

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

    public Double getQt_parcelas() {
        return qt_parcelas;
    }

    public void setQt_parcelas(Double qt_parcelas) {
        this.qt_parcelas = qt_parcelas;
    }

    public Double getQt_parcelas_restantes() {
        return qt_parcelas_restantes;
    }

    public void setQt_parcelas_restantes(Double qt_parcelas_restantes) {
        this.qt_parcelas_restantes = qt_parcelas_restantes;
    }

    public BigDecimal getVl_financiado() {
        return vl_financiado;
    }

    public void setVl_financiado(BigDecimal vl_financiado) {
        this.vl_financiado = vl_financiado;
    }

    public BigDecimal getVl_parcela() {
        return vl_parcela;
    }

    public void setVl_parcela(BigDecimal vl_parcela) {
        this.vl_parcela = vl_parcela;
    }

    public BigDecimal getVl_devedor() {
        return vl_devedor;
    }

    public void setVl_devedor(BigDecimal vl_devedor) {
        this.vl_devedor = vl_devedor;
    }

    public Date getDt_quitacao_prev() {
        return dt_quitacao_prev;
    }

    public void setDt_quitacao_prev(Date dt_quitacao_prev) {
        this.dt_quitacao_prev = dt_quitacao_prev;
    }

    public String getTp_objeto() {
        return tp_objeto;
    }

    public void setTp_objeto(String tp_objeto) {
        this.tp_objeto = tp_objeto;
    }
    
}
