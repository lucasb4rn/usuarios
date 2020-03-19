/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.dao;

import br.com.usuarios.DB.JPAUtil;
import br.com.usuarios.sistema.OrdemServico;
import br.com.usuarios.sistema.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Lucas
 */
public class OrdemServicoDao {

    public List<OrdemServico> pesquisaOrdemServicoFiltro(String statusFilter, boolean dataCheck, String data, boolean valorCheck, double valor) {
        try {

            List listaWhere = new ArrayList();
            listaWhere.add("status = '" + statusFilter + "'");

            String queryString = "select * from sis_ordem_servico";

            if (dataCheck) {
                listaWhere.add("dt_criacao = '" + data + "'");
            }

            for (int i = 0; i < listaWhere.size(); i++) {
                if (i == 0) {
                    queryString += " WHERE " + listaWhere.get(i) + " \n";
                } else {
                    queryString += " AND " + listaWhere.get(i) + " \n";
                }
            }

            Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, OrdemServico.class);
            return qry.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<Produto> pesquisaProdutoDescricao(String caixaPesquisa) {

        String queryString = "select * from produto where descricao like '%" + caixaPesquisa + "%'";
        Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, Produto.class);

        return qry.getResultList();
    }

}
