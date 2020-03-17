package br.com.usuarios.DB;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DAO<T> {

    private final Class<T> classe;

    public DAO(Class<T> classe) {
        this.classe = classe;
    }

    public boolean save(T t) {

        EntityManager em = new JPAUtil().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            em.close();
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public boolean delete(T t) {

        EntityManager em = new JPAUtil().getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.merge(t));
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void atualiza(T t) {

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
        em.close();
    }

    public List<T> listaTodos() {

        try {
            EntityManager em = new JPAUtil().getEntityManager();
            CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
            query.select(query.from(classe));
            List<T> lista = em.createQuery(query).getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            return new ArrayList();
        }

    }

    public T buscaPorId(Integer id) {

        try {
            EntityManager em = new JPAUtil().getEntityManager();
            T instancia = em.find(classe, id);
            em.close();
            return instancia;
        } catch (Exception e) {
            return null;
        }

    }

    public List<T> buscaPorNome(String nome) {

        EntityManager em = new JPAUtil().getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
        Root<T> root = query.from(classe);

        List<Predicate> predicates = new ArrayList<Predicate>();

        Path<String> nomePath = root.<String>get("nome");

        if (!nome.isEmpty()) {
            Predicate nomeIgual = criteriaBuilder.like(nomePath, "%" + nome + "%");
            predicates.add(nomeIgual);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));

        TypedQuery<T> typedQuery = em.createQuery(query);

        List<T> lista = em.createQuery(query).getResultList();

        em.close();

        return typedQuery.getResultList();

    }

    public int contaTodos() {
        EntityManager em = new JPAUtil().getEntityManager();
        long result = (Long) em.createQuery("select count(n) from usuarios n")
                .getSingleResult();
        em.close();

        return (int) result;
    }

    public List<T> listaTodasPaginada(int firstResult, int maxResults) {
        EntityManager em = new JPAUtil().getEntityManager();
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
        query.select(query.from(classe));

        List<T> lista = em.createQuery(query).setFirstResult(firstResult)
                .setMaxResults(maxResults).getResultList();

        em.close();
        return lista;
    }

}
