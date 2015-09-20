/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marco
 */
@Entity
@Table(catalog = "empregos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reuniao.findAll", query = "SELECT r FROM Reuniao r"),
    @NamedQuery(name = "Reuniao.findById", query = "SELECT r FROM Reuniao r WHERE r.id = :id"),
    @NamedQuery(name = "Reuniao.findByDataReuniao", query = "SELECT r FROM Reuniao r WHERE r.dataReuniao = :dataReuniao")})
public class Reuniao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "data_reuniao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataReuniao;
    @JoinColumn(name = "empresa_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Empresa empresaId;
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Pessoa pessoaId;

    public Reuniao() {
    }

    public Reuniao(Integer id) {
        this.id = id;
    }

    public Reuniao(Integer id, Date dataReuniao) {
        this.id = id;
        this.dataReuniao = dataReuniao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataReuniao() {
        return dataReuniao;
    }

    public void setDataReuniao(Date dataReuniao) {
        this.dataReuniao = dataReuniao;
    }

    public Empresa getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Empresa empresaId) {
        this.empresaId = empresaId;
    }

    public Pessoa getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Pessoa pessoaId) {
        this.pessoaId = pessoaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reuniao)) {
            return false;
        }
        Reuniao other = (Reuniao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Reuniao[ id=" + id + " ]";
    }
    
}
