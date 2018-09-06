/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucas.andrade
 */
@Entity
public class Anexo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private byte[] anexo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dh_anexado;
    @ManyToOne
    @JoinColumn(name="CD_TAREFA")
    private Tarefa tarefa;
    
    public Anexo() {
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public Date getDh_anexado() {
        return dh_anexado;
    }

    public void setDh_anexado(Date dh_anexado) {
        this.dh_anexado = dh_anexado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Arrays.hashCode(this.anexo);
        hash = 37 * hash + Objects.hashCode(this.dh_anexado);
        hash = 37 * hash + Objects.hashCode(this.tarefa);
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
        final Anexo other = (Anexo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Arrays.equals(this.anexo, other.anexo)) {
            return false;
        }
        if (!Objects.equals(this.dh_anexado, other.dh_anexado)) {
            return false;
        }
        if (!Objects.equals(this.tarefa, other.tarefa)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return id.toString();
    }
    
 
}
