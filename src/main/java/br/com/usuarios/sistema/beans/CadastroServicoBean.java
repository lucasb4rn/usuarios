/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.beans;

import br.com.usuarios.DB.DAO;
import br.com.usuarios.sistema.Produto;
import br.com.usuarios.sistema.Servico;
import br.com.usuarios.sistema.dao.ServicoDao;
import br.com.usuarios.utilitarios.ChamadaPaginaBean;
import br.com.usuarios.utilitarios.Messages;
import br.com.usuarios.utilitarios.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CadastroServicoBean implements Serializable {

    private Servico servico;
    private String caixaPesquisa;
    private List<Servico> listaServicoPesquisa;
    private String filter;

    public CadastroServicoBean() {
        listaServicoPesquisa = new ArrayList();
        filter = "";
        servico = new Servico();
        caixaPesquisa = "";
    }

    public String destroy() {
        return ChamadaPaginaBean.pagina("cadastroServico");
    }

    public void save() {

        DAO dao = new DAO(Produto.class);

        if (servico.getDescricao().length() < 5) {
            Messages.error("Descrição precisa conter mais que 5 caracteres.");
            return;
        }

        if (servico.getValor() < 0) {
            Messages.error("Valor não pode ser negativo");
            return;
        }

        if (servico.getId() == null) {
            if (!dao.save(servico)) {
                Messages.error("Erro ao salvar!");
                return;
            } else {
                Messages.info("Salvo com sucesso!!");
                servico = new Servico();
                return;
            }
        } else {
            if (!dao.atualiza(servico)) {
                Messages.error("Erro ao atualizar!");
                return;
            } else {
                Messages.info("Atualizado com sucesso!!");
                servico = new Servico();
                return;
            }
        }

    }

    public void deletar() {
        DAO dao = new DAO(Produto.class);
        if (servico.getId() != null) {
            if (!dao.delete(servico)) {
                Messages.error("Erro ao deletar servico!");
                return;
            } else {
                Messages.info("Servico deletado com sucesso!!");
                servico = new Servico();
                return;
            }
        }
        Messages.error("Selecione um seviço antes de excluir!");
    }

    public void findServicoFiltro() {
        listaServicoPesquisa.clear();
        listaServicoPesquisa = new ServicoDao().pesquisaServicoFiltro(filter, caixaPesquisa);
    }

    public String redireciona(Servico s) {
        Sessions.put("servicoPesquisa", s);
        return ChamadaPaginaBean.urlRetorno();
    }

    public Servico getServico() {
        if (Sessions.exists("servicoPesquisa")) {
            servico = (Servico) Sessions.getObject("servicoPesquisa", true);
        }
        return servico;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getCaixaPesquisa() {
        return caixaPesquisa;
    }

    public void setCaixaPesquisa(String caixaPesquisa) {
        this.caixaPesquisa = caixaPesquisa;
    }

    public List<Servico> getListaServicoPesquisa() {
        return listaServicoPesquisa;
    }

    public void setListaServicoPesquisa(List<Servico> listaServicoPesquisa) {
        this.listaServicoPesquisa = listaServicoPesquisa;
    }

}
