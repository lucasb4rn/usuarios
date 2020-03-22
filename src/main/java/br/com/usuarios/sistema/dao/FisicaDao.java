/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.usuarios.sistema.dao;

import br.com.usuarios.DB.JPAUtil;
import br.com.usuarios.sistema.Fisica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Lucas
 */
public class FisicaDao {

    public List<Fisica> pesquisaPessoaFiltro(String filter, String term) {
        try {

            List listaWhere = new ArrayList();

            String queryString = "select * from pes_fisica";

            switch (filter) {

                case "cpf":
                    listaWhere.add("ds_documento = '" + term + "'");
                    break;
                case "nome":
                    listaWhere.add("ds_nome like '%" + term + "%'");
                    break;
                case "email":
                    listaWhere.add("email like '%" + term + "%'");
                    break;

            }

            for (int i = 0; i < listaWhere.size(); i++) {
                if (i == 0) {
                    queryString += " WHERE " + listaWhere.get(i) + " \n";
                } else {
                    queryString += " AND " + listaWhere.get(i) + " \n";
                }
            }

            Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, Fisica.class);
            return qry.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public Fisica pesquisaPessoaPorCPF(String documento) {
        
        try {
            String queryString = "select * from pes_fisica where ds_documento = '" + documento + "'";
            Query qry = new JPAUtil().getEntityManager().createNativeQuery(queryString, Fisica.class);
            return (Fisica) qry.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}
