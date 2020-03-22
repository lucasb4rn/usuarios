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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "fin_saida_produto")
public class SaidaProduto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_fisica", nullable = false)
    private Fisica fisica;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "id_produto_fatura_item")
    private List<ProdutoFaturaItem> listaProdutoFaturaItem;
    @Column(name = "vl_valor_total", precision = 10, scale = 2)
    private double valorTotal;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_cadastro")
    private Date dataCriacao;
    @Column(name = "qt_total", precision = 10, scale = 2)
    private int quantidadeTotal;

    public SaidaProduto() {
        this.id = null;
        this.listaProdutoFaturaItem = new ArrayList();
        this.dataCriacao = Dates.dataHoje();
        this.quantidadeTotal = 0;
    }

    public SaidaProduto(Integer id, Fisica fisica, List<ProdutoFaturaItem> listaProdutoFaturaItem, double valorTotal, Date dataCriacao, int quantidadeTotal) {
        this.id = id;
        this.fisica = fisica;
        this.listaProdutoFaturaItem = listaProdutoFaturaItem;
        this.valorTotal = valorTotal;
        this.dataCriacao = dataCriacao;
        this.quantidadeTotal = quantidadeTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Fisica getFisica() {
        return fisica;
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

    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public String getDataCriacaoString() {
        return Dates.converteData(dataCriacao);
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public List<ProdutoFaturaItem> getListaProdutoFaturaItem() {
        return listaProdutoFaturaItem;
    }

    public void setListaProdutoFaturaItem(List<ProdutoFaturaItem> listaProdutoFaturaItem) {
        this.listaProdutoFaturaItem = listaProdutoFaturaItem;
    }

}
