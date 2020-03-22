/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.dao;

import br.com.usuarios.DB.JPAUtil;
import br.com.usuarios.sistema.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

public class ProdutoDao {

    public List<Produto> pesquisaProdutoFiltro(String filter, String caixaPesquisa) {
        try {

            List listaWhere = new ArrayList();

            String queryString = "select * from prod_produto";

            switch (filter) {
                case "descricao":
                    listaWhere.add("ds_descricao like '%" + caixaPesquisa + "%'");
                    break;
                case "EAN":
                    listaWhere.add("ean like '%" + caixaPesquisa + "%'");
                    break;
            }

            for (int i = 0; i < listaWhere.size(); i++) {
                if (i == 0) {
                    queryString += " WHERE " + listaWhere.get(i) + " \n";
                } else {
                    queryString += " AND " + listaWhere.get(i) + " \n";
                }
            }

            Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, Produto.class);
            return qry.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<Produto> pesquisaProdutoDescricao(String caixaPesquisa) {

        String queryString = "select * from prod_produto where ds_descricao like '%" + caixaPesquisa + "%'";
        Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, Produto.class);
        
        return qry.getResultList();
    }

}
