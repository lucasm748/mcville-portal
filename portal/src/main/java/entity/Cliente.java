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
import javax.persistence.ManyToOne;
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
    private String dt_admissao;
    @Temporal(TemporalType.DATE)
    private String dt_nascimento_conjuge;
    @Temporal(TemporalType.DATE)
    private String dt_admissao_conjuge;
    @Temporal(TemporalType.DATE)
    private String dt_venda_casa;
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
}
