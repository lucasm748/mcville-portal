/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author Lucas
 */
@Entity
//@DiscriminatorValue(value = "J")
@PrimaryKeyJoinColumn(name="idPessoa")
public class PessoaJ extends Pessoa{

    @Column(length = 15, unique = true, nullable = false)
    private String cnpj;
    @Column(length = 15)
    private String inscricao;
    @Column(length = 1)
    private Integer fornecedor;

    public PessoaJ() {
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Integer fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }
}