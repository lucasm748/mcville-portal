/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author MC Ville
 */
@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String ds_nome;
    @Column
    private String cpf;
    @Column
    private String ds_empregador;
    @Column
    private String email;
    @Column
    private String telefone;
    @Column
    private String telefone_2;
    @Column
    private String celular;
    @Column
    private String ds_endereco;
    @Column
    private String cep;
    @Column
    private String ds_nome_conjuge;
    @Column
    private String ds_empregador_conjuge;
    @Column
    private String email_conjuge;
    @Column
    private String telefone_conjuge;
    @Column
    private String telefone_2_conjuge;
    @Column
    private String celular_conjuge;
    @Temporal(TemporalType.DATE)
    private Date dt_nascimento;
    @Temporal(TemporalType.DATE)
    private Date dt_admissao;
    @Temporal(TemporalType.DATE)
    private Date dt_nascimento_conjuge;
    @Temporal(TemporalType.DATE)
    private Date dt_admissao_conjuge;
    @Temporal(TemporalType.DATE)
    private Date dt_venda_casa;
    @Column
    private String tp_estado_civil;
    @Column
    private String tp_horario_contato;
    @Column
    private String tp_estado_civil_conjuge;
    @Column
    private String tp_horario_contato_conjuge;
    @Column
    private String cpf_conjuge;
    @Column
    private String tp_possui_casa;
    @Column
    private String tp_possuiu_casa;
    @Column
    private String tp_paga_aluguel;
    @Column
    private String tp_casa_transferida;
    @Column
    private BigDecimal vl_fgts;
    @Column
    private BigDecimal vl_fgts_conjuge;
    @Column
    private BigDecimal vl_renda_familiar;
    @Column
    private BigDecimal vl_fgts_total;
    @Column
    private BigDecimal vl_aluguel;
    @Column
    private String cad_mut;
    @ManyToOne
    private Cidade cidade;
    @Column
    private String ds_observacao;
    @ManyToOne
    private Status status;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Dependente> dependentes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Financiamento> financiamentos;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Restricao> restricoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDs_nome() {
        return ds_nome;
    }

    public void setDs_nome(String ds_nome) {
        this.ds_nome = ds_nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDs_empregador() {
        return ds_empregador;
    }

    public void setDs_empregador(String ds_empregador) {
        this.ds_empregador = ds_empregador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone_2() {
        return telefone_2;
    }

    public void setTelefone_2(String telefone_2) {
        this.telefone_2 = telefone_2;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDs_endereco() {
        return ds_endereco;
    }

    public void setDs_endereco(String ds_endereco) {
        this.ds_endereco = ds_endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getDs_nome_conjuge() {
        return ds_nome_conjuge;
    }

    public void setDs_nome_conjuge(String ds_nome_conjuge) {
        this.ds_nome_conjuge = ds_nome_conjuge;
    }

    public String getDs_empregador_conjuge() {
        return ds_empregador_conjuge;
    }

    public void setDs_empregador_conjuge(String ds_empregador_conjuge) {
        this.ds_empregador_conjuge = ds_empregador_conjuge;
    }

    public String getEmail_conjuge() {
        return email_conjuge;
    }

    public void setEmail_conjuge(String email_conjuge) {
        this.email_conjuge = email_conjuge;
    }

    public String getTelefone_conjuge() {
        return telefone_conjuge;
    }

    public void setTelefone_conjuge(String telefone_conjuge) {
        this.telefone_conjuge = telefone_conjuge;
    }

    public String getTelefone_2_conjuge() {
        return telefone_2_conjuge;
    }

    public void setTelefone_2_conjuge(String telefone_2_conjuge) {
        this.telefone_2_conjuge = telefone_2_conjuge;
    }

    public String getCelular_conjuge() {
        return celular_conjuge;
    }

    public void setCelular_conjuge(String celular_conjuge) {
        this.celular_conjuge = celular_conjuge;
    }

    public Date getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(Date dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public Date getDt_admissao() {
        return dt_admissao;
    }

    public void setDt_admissao(Date dt_admissao) {
        this.dt_admissao = dt_admissao;
    }

    public Date getDt_nascimento_conjuge() {
        return dt_nascimento_conjuge;
    }

    public void setDt_nascimento_conjuge(Date dt_nascimento_conjuge) {
        this.dt_nascimento_conjuge = dt_nascimento_conjuge;
    }

    public Date getDt_admissao_conjuge() {
        return dt_admissao_conjuge;
    }

    public void setDt_admissao_conjuge(Date dt_admissao_conjuge) {
        this.dt_admissao_conjuge = dt_admissao_conjuge;
    }

    public Date getDt_venda_casa() {
        return dt_venda_casa;
    }

    public void setDt_venda_casa(Date dt_venda_casa) {
        this.dt_venda_casa = dt_venda_casa;
    }


    public String getTp_estado_civil() {
        return tp_estado_civil;
    }

    public void setTp_estado_civil(String tp_estado_civil) {
        this.tp_estado_civil = tp_estado_civil;
    }

    public String getTp_horario_contato() {
        return tp_horario_contato;
    }

    public void setTp_horario_contato(String tp_horario_contato) {
        this.tp_horario_contato = tp_horario_contato;
    }

    public String getTp_estado_civil_conjuge() {
        return tp_estado_civil_conjuge;
    }

    public void setTp_estado_civil_conjuge(String tp_estado_civil_conjuge) {
        this.tp_estado_civil_conjuge = tp_estado_civil_conjuge;
    }

    public String getTp_horario_contato_conjuge() {
        return tp_horario_contato_conjuge;
    }

    public void setTp_horario_contato_conjuge(String tp_horario_contato_conjuge) {
        this.tp_horario_contato_conjuge = tp_horario_contato_conjuge;
    }

    public String getCpf_conjuge() {
        return cpf_conjuge;
    }

    public void setCpf_conjuge(String cpf_conjuge) {
        this.cpf_conjuge = cpf_conjuge;
    }

    public String getTp_possui_casa() {
        return tp_possui_casa;
    }

    public void setTp_possui_casa(String tp_possui_casa) {
        this.tp_possui_casa = tp_possui_casa;
    }

    public String getTp_possuiu_casa() {
        return tp_possuiu_casa;
    }

    public void setTp_possuiu_casa(String tp_possuiu_casa) {
        this.tp_possuiu_casa = tp_possuiu_casa;
    }

    public String getTp_paga_aluguel() {
        return tp_paga_aluguel;
    }

    public void setTp_paga_aluguel(String tp_paga_aluguel) {
        this.tp_paga_aluguel = tp_paga_aluguel;
    }

    public String getTp_casa_transferida() {
        return tp_casa_transferida;
    }

    public void setTp_casa_transferida(String tp_casa_transferida) {
        this.tp_casa_transferida = tp_casa_transferida;
    }

    public BigDecimal getVl_fgts() {
        return vl_fgts;
    }

    public void setVl_fgts(BigDecimal vl_fgts) {
        this.vl_fgts = vl_fgts;
    }

    public BigDecimal getVl_fgts_conjuge() {
        return vl_fgts_conjuge;
    }

    public void setVl_fgts_conjuge(BigDecimal vl_fgts_conjuge) {
        this.vl_fgts_conjuge = vl_fgts_conjuge;
    }

    public BigDecimal getVl_renda_familiar() {
        return vl_renda_familiar;
    }

    public void setVl_renda_familiar(BigDecimal vl_renda_familiar) {
        this.vl_renda_familiar = vl_renda_familiar;
    }

    public BigDecimal getVl_fgts_total() {
        return vl_fgts_total;
    }

    public void setVl_fgts_total(BigDecimal vl_fgts_total) {
        this.vl_fgts_total = vl_fgts_total;
    }

    public BigDecimal getVl_aluguel() {
        return vl_aluguel;
    }

    public void setVl_aluguel(BigDecimal vl_aluguel) {
        this.vl_aluguel = vl_aluguel;
    }

    public String getCad_mut() {
        return cad_mut;
    }

    public void setCad_mut(String cad_mut) {
        this.cad_mut = cad_mut;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getDs_observacao() {
        return ds_observacao;
    }

    public void setDs_observacao(String ds_observacao) {
        this.ds_observacao = ds_observacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Dependente> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

    public List<Financiamento> getFinanciamentos() {
        return financiamentos;
    }

    public void setFinanciamentos(List<Financiamento> financiamentos) {
        this.financiamentos = financiamentos;
    }

    public List<Restricao> getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(List<Restricao> restricoes) {
        this.restricoes = restricoes;
    }
    
}
