/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema;

// O sistema deve possuir um cadastro de produtos, com as informações Código EAN, Nome do produto, Preço de custo e Preço de venda;
import br.com.usuarios.utilitarios.Currency;
import br.com.usuarios.utilitarios.Dates;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "prod_produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 13)
    private String EAN;
    @Column(name = "ds_descricao", nullable = false, length = 100)
    private String descricao;
    @Column(name = "vl_preco_custo", precision = 10, scale = 2)
    private Double precoCusto;
    @Column(name = "vl_preco_venda", precision = 10, scale = 2)
    private Double precoVenda;
    @Column(name = "dt_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    public Produto() {
        this.descricao = "";
        this.dataCadastro = Dates.dataHoje();
        this.precoCusto = new Double(0);
        this.precoVenda = new Double(0);
        this.dataCadastro = Dates.dataHoje();

    }

    public Produto(String EAN, String descricao, Double precoCusto, Double precoVenda) {
        this.EAN = EAN;
        this.descricao = descricao;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.dataCadastro = Dates.dataHoje();
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public String getPrecoVendaString() {
        return Currency.converteDoubleToString(precoVenda, 2);
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
