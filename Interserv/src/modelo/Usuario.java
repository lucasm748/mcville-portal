/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Lucas
 */
@Entity
public class Usuario extends Pessoa{

      @Column(length = 11)
    private String cpf;
    @Column
    private Integer acesso;
    @Column (length=15)
    private String rg;
    @Column(length=50)
    private String senha;

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
  
  
    public Integer getAcesso() {
        return acesso;
    }

    public void setAcesso(Integer acesso) {
        this.acesso = acesso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Usuario() {
    }
    
  

}
  