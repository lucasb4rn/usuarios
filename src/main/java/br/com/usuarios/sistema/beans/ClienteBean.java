/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Fisica;
import br.com.usuarios.utilitarios.Dates;
import br.com.usuarios.utilitarios.Messages;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ClienteBean {

    private Fisica fisica;
    private String selectedDtNascimento;

    public ClienteBean() {
        fisica = new Fisica();
    }

    public void save() {

        DAO dao = new DAO(Fisica.class);

        if (!dao.save(fisica)) {
            Messages.error("Erro ao salvar!");
        } else {
            Messages.info("Salvo com sucesso!!");
        }

    }

    public Fisica getFisica() {
        return fisica;
    }

    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }

    public String getSelectedDtNascimento() {
        return selectedDtNascimento;
    }

    public void setSelectedDtNascimento(String selectedDtNascimento) {
        fisica.setDataNascimento(Dates.converte(selectedDtNascimento));
    }

}
