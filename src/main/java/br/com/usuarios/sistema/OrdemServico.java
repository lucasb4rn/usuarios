/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema;

import br.com.usuarios.utilitarios.Dates;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sis_ordem_servico")
public class OrdemServico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "id_fisica")
    private Fisica fisica;
    @OneToMany
    @JoinColumn(name = "id_produto")
    private List<Produto> listaProdutos;
    @Enumerated(EnumType.STRING)
    private OrdemStatus status;

    @Column(name = "dt_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    @OneToMany
    @JoinColumn(name = "id_servico")
    private List<Servico> listaServicos;

    public OrdemServico() {
        this.id = null;
        this.fisica = new Fisica();
        this.status = OrdemStatus.AFazer;
        this.listaProdutos = new ArrayList();
        this.dataCadastro = Dates.dataHoje();
        this.listaServicos = new ArrayList();
    }

    public OrdemServico(Integer id, Fisica fisica, List<Produto> listaProdutos, OrdemStatus status, List<Servico> listaServico) {
        this.id = id;
        this.fisica = fisica;
        this.listaProdutos = listaProdutos;
        this.status = status;
        this.dataCadastro = Dates.dataHoje();
        this.listaServicos = listaServico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fisica getFisica() {
        return fisica;
    }

    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public String getDataCadastroString() {
        return Dates.converteData(dataCadastro);
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Produto> getListaProdutosPesquisa() {
        if (Sessions.exists("produtoPesquisa")) {
            this.listaProdutos.add((Produto) Sessions.getObject("produtoPesquisa", true));
        }
        return listaProdutos;
    }

    public OrdemStatus getStatus() {
        return status;
    }

    public void setStatus(OrdemStatus status) {
        this.status = status;
    }

    public List<Servico> getListaServicosPesquisa() {
        if (Sessions.exists("servicoPesquisa")) {
            this.listaServicos.add((Servico) Sessions.getObject("servicoPesquisa", true));
        }
        return listaServicos;
    }

    public void setListaServicos(List<Servico> listaServicos) {
        this.listaServicos = listaServicos;
    }

    public List<Servico> getListaServicos() {
        return listaServicos;
    }

}
