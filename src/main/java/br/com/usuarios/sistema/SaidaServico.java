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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "fin_saida_servico")
public class SaidaServico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "id_fisica", nullable = false)
    private Fisica fisica;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "id_produto_fatura_item")
    private List<ProdutoFaturaItem> listaProdutoFaturaItem;
    @Enumerated(EnumType.STRING)
    private OrdemStatus status;
    @Column(name = "dt_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_servico_fatura_item")
    private List<ServicoFaturaItem> listaServicoFaturaItem;
    @Column(name = "vl_valor_total", precision = 10, scale = 2)
    private double valorTotal;
    @Column(name = "qt_total")
    private int quantidadeTotal;

    public SaidaServico() {
        this.id = null;
        this.fisica = new Fisica();
        this.status = OrdemStatus.AFazer;
        this.listaProdutoFaturaItem = new ArrayList();
        this.dataCadastro = Dates.dataHoje();
        this.listaServicoFaturaItem = new ArrayList();
        this.quantidadeTotal = 0;
        this.valorTotal = new Double(0);
    }

    public SaidaServico(Integer id, Fisica fisica, List<ProdutoFaturaItem> listaProdutoFaturaItem, OrdemStatus status, Date dataCadastro, List<ServicoFaturaItem> listaServicoFaturaItem, double valorTotal, int quantidadeTotal) {
        this.id = id;
        this.fisica = fisica;
        this.listaProdutoFaturaItem = listaProdutoFaturaItem;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.listaServicoFaturaItem = listaServicoFaturaItem;
        this.valorTotal = valorTotal;
        this.quantidadeTotal = quantidadeTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public String getDataCriacaoString() {
        return Dates.converteData(dataCadastro);
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<ProdutoFaturaItem> getListaProdutosPesquisa() {

        if (Sessions.exists("produtoPesquisa")) {
            Produto produto = new Produto();
            produto = (Produto) Sessions.getObject("produtoPesquisa", true);
            ProdutoFaturaItem produtoFaturaItem = new ProdutoFaturaItem();
            produtoFaturaItem.setProduto(produto);
            listaProdutoFaturaItem.add(produtoFaturaItem);
        }

        return listaProdutoFaturaItem;
    }

    public List<ServicoFaturaItem> getListaServicosPesquisa() {
        if (Sessions.exists("servicoPesquisa")) {

            Servico servico = new Servico();
            servico = (Servico) Sessions.getObject("servicoPesquisa", true);
            ServicoFaturaItem servicoFaturaItem = new ServicoFaturaItem();
            servicoFaturaItem.setServico(servico);
            listaServicoFaturaItem.add(servicoFaturaItem);
        }
        return listaServicoFaturaItem;
    }

    public List<ProdutoFaturaItem> getListaProdutoFaturaItem() {
        return listaProdutoFaturaItem;
    }

    public void setListaProdutoFaturaItem(List<ProdutoFaturaItem> listaProdutoFaturaItem) {
        this.listaProdutoFaturaItem = listaProdutoFaturaItem;
    }

    public OrdemStatus getStatus() {
        return status;
    }

    public void setStatus(OrdemStatus status) {
        this.status = status;
    }

    public List<ServicoFaturaItem> getListaServicoFaturaItem() {
        return listaServicoFaturaItem;
    }

    public void setListaServicoFaturaItem(List<ServicoFaturaItem> listaServicoFaturaItem) {
        this.listaServicoFaturaItem = listaServicoFaturaItem;
    }

}
