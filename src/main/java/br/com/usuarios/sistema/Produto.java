/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema;

// O sistema deve possuir um cadastro de produtos, com as informações Código EAN, Nome do produto, Preço de custo e Preço de venda;
public class Produto {

    private String EAN;
    private String descricao;
    private Double precoCusto;
    private Double precoVenda;

    public Produto() {
    }

    public Produto(String EAN, String descricao, Double precoCusto, Double precoVenda) {
        this.EAN = EAN;
        this.descricao = descricao;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
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

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

}
