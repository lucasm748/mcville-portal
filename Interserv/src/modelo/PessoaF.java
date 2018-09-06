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
//@DiscriminatorValue(value = "F")
@PrimaryKeyJoinColumn(name="idPessoa")
public class PessoaF extends Pessoa {

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;
    @Column(length = 15)
    private String rg;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public PessoaF() {
    }
}
