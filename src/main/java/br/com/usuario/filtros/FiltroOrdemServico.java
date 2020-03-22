/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuario.filtros;

import java.io.Serializable;

/**
 *
 * @author Lucas
 */
public class FiltroOrdemServico implements Serializable{

    private boolean dataCheck;
    private boolean valorCheck;
    private boolean clienteCheck;

    private double valor;
    private String dataCriacao;
    private String statusOrdem;
    private String clienteNome;

    public FiltroOrdemServico() {
    }

    public FiltroOrdemServico(boolean dataCheck, boolean valorCheck, boolean clienteCheck, double valor, String dataCriacao, String statusOrdem, String clienteNome) {
        this.dataCheck = dataCheck;
        this.valorCheck = valorCheck;
        this.clienteCheck = clienteCheck;
        this.valor = valor;
        this.dataCriacao = dataCriacao;
        this.statusOrdem = statusOrdem;
        this.clienteNome = clienteNome;
    }

    public boolean isDataCheck() {
        return dataCheck;
    }

    public void setDataCheck(boolean dataCheck) {
        this.dataCheck = dataCheck;
    }

    public boolean isValorCheck() {
        return valorCheck;
    }

    public void setValorCheck(boolean valorCheck) {
        this.valorCheck = valorCheck;
    }

    public boolean isClienteCheck() {
        return clienteCheck;
    }

    public void setClienteCheck(boolean clienteCheck) {
        this.clienteCheck = clienteCheck;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatusOrdem() {
        return statusOrdem;
    }

    public void setStatusOrdem(String statusOrdem) {
        this.statusOrdem = statusOrdem;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

}
