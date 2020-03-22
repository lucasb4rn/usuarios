/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuario.filtros;

import java.io.Serializable;

public class FiltroSaidaProduto implements Serializable {

    private boolean checkCliente;
    private boolean checkDataCriacao;
    private String clienteNome;
    private String dataCadastro;

    public FiltroSaidaProduto() {
    }

    public FiltroSaidaProduto(boolean checkCliente, boolean checkDataCriacao, String clienteNome, String dataCadastro) {
        this.checkCliente = checkCliente;
        this.checkDataCriacao = checkDataCriacao;
        this.clienteNome = clienteNome;
        this.dataCadastro = dataCadastro;
    }

    public boolean isCheckCliente() {
        return checkCliente;
    }

    public void setCheckCliente(boolean checkCliente) {
        this.checkCliente = checkCliente;
    }

    public boolean isCheckDataCriacao() {
        return checkDataCriacao;
    }

    public void setCheckDataCriacao(boolean checkDataCriacao) {
        this.checkDataCriacao = checkDataCriacao;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
