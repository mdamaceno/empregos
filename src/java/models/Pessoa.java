/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marco
 */
@Entity
@Table(catalog = "empregos", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p"),
    @NamedQuery(name = "Pessoa.findById", query = "SELECT p FROM Pessoa p WHERE p.id = :id"),
    @NamedQuery(name = "Pessoa.findByNome", query = "SELECT p FROM Pessoa p WHERE p.nome = :nome"),
    @NamedQuery(name = "Pessoa.findByEmail", query = "SELECT p FROM Pessoa p WHERE p.email = :email"),
    @NamedQuery(name = "Pessoa.findByTelefone", query = "SELECT p FROM Pessoa p WHERE p.telefone = :telefone"),
    @NamedQuery(name = "Pessoa.findByCelular", query = "SELECT p FROM Pessoa p WHERE p.celular = :celular"),
    @NamedQuery(name = "Pessoa.findByEscolaridade", query = "SELECT p FROM Pessoa p WHERE p.escolaridade = :escolaridade"),
    @NamedQuery(name = "Pessoa.findByFuncao1", query = "SELECT p FROM Pessoa p WHERE p.funcao1 = :funcao1"),
    @NamedQuery(name = "Pessoa.findByFuncao2", query = "SELECT p FROM Pessoa p WHERE p.funcao2 = :funcao2"),
    @NamedQuery(name = "Pessoa.findByFuncao3", query = "SELECT p FROM Pessoa p WHERE p.funcao3 = :funcao3"),
    @NamedQuery(name = "Pessoa.findBySenha", query = "SELECT p FROM Pessoa p WHERE p.senha = :senha"),
    @NamedQuery(name = "Pessoa.findByEmpregado", query = "SELECT p FROM Pessoa p WHERE p.empregado = :empregado"),
    @NamedQuery(name = "Pessoa.findByLoginAndPassword", query = "SELECT p FROM Pessoa p WHERE p.email = :ema AND p.senha = :pass")})
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String nome;
    @Basic(optional = false)
    @Column(nullable = false, length = 150)
    private String email;
    @Column(length = 10)
    private String telefone;
    @Basic(optional = false)
    @Column(nullable = false, length = 11)
    private String celular;
    @Column(length = 12)
    private String escolaridade;
    private Integer funcao1;
    private Integer funcao2;
    private Integer funcao3;
    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String senha;
    private Boolean empregado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaId")
    private List<Reuniao> reuniaoList;

    public Pessoa() {
    }

    public Pessoa(Integer id) {
        this.id = id;
    }

    public Pessoa(Integer id, String nome, String email, String celular, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public Integer getFuncao1() {
        return funcao1;
    }

    public void setFuncao1(Integer funcao1) {
        this.funcao1 = funcao1;
    }

    public Integer getFuncao2() {
        return funcao2;
    }

    public void setFuncao2(Integer funcao2) {
        this.funcao2 = funcao2;
    }

    public Integer getFuncao3() {
        return funcao3;
    }

    public void setFuncao3(Integer funcao3) {
        this.funcao3 = funcao3;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getEmpregado() {
        return empregado;
    }

    public void setEmpregado(Boolean empregado) {
        this.empregado = empregado;
    }

    @XmlTransient
    public List<Reuniao> getReuniaoList() {
        return reuniaoList;
    }

    public void setReuniaoList(List<Reuniao> reuniaoList) {
        this.reuniaoList = reuniaoList;
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
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Pessoa[ id=" + id + " ]";
    }

}
