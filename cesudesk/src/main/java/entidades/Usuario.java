/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author lucas.andrade
 */
@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cd_usuario;
    @Column
    private String nm_usuario;
    @Column
    private String nm_pessoa;
    @Column
    private String senha_usuario;
    @ManyToOne(fetch = FetchType.EAGER)
    private Cargo cargo;
    @Column
    private Boolean ativo;

    public Long getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(Long cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public String getNm_usuario() {
        return nm_usuario;
    }

    public void setNm_usuario(String nm_usuario) {
        this.nm_usuario = nm_usuario;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getNm_pessoa() {
        return nm_pessoa;
    }

    public void setNm_pessoa(String nm_pessoa) {
        this.nm_pessoa = nm_pessoa;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.cd_usuario != null ? this.cd_usuario.hashCode() : 0);
        hash = 29 * hash + (this.nm_usuario != null ? this.nm_usuario.hashCode() : 0);
        hash = 29 * hash + (this.nm_pessoa != null ? this.nm_pessoa.hashCode() : 0);
        hash = 29 * hash + (this.senha_usuario != null ? this.senha_usuario.hashCode() : 0);
        hash = 29 * hash + (this.cargo != null ? this.cargo.hashCode() : 0);
        hash = 29 * hash + (this.ativo != null ? this.ativo.hashCode() : 0);
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
        final Usuario other = (Usuario) obj;
        if ((this.nm_usuario == null) ? (other.nm_usuario != null) : !this.nm_usuario.equals(other.nm_usuario)) {
            return false;
        }
        if ((this.nm_pessoa == null) ? (other.nm_pessoa != null) : !this.nm_pessoa.equals(other.nm_pessoa)) {
            return false;
        }
        if ((this.senha_usuario == null) ? (other.senha_usuario != null) : !this.senha_usuario.equals(other.senha_usuario)) {
            return false;
        }
        if (this.cd_usuario != other.cd_usuario && (this.cd_usuario == null || !this.cd_usuario.equals(other.cd_usuario))) {
            return false;
        }
        if (this.cargo != other.cargo && (this.cargo == null || !this.cargo.equals(other.cargo))) {
            return false;
        }
        if (this.ativo != other.ativo && (this.ativo == null || !this.ativo.equals(other.ativo))) {
            return false;
        }
        return true;
    }

    
}
