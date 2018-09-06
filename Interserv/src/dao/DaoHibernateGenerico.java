package dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.management.Query;
import modelo.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import modelo.Pessoa;
import modelo.Venda;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

public class DaoHibernateGenerico implements DaoGenerico {

    @Override
    public Object save(Object objeto) {
        try {
            Object obj = null;
            HibernateUtil.beginTransaction();
            obj = HibernateUtil.getSession().merge(objeto);
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            return obj;
        } catch (HibernateException hibernateException) {
            HibernateUtil.rollbackTransaction();
            HibernateUtil.closeSession();
            throw hibernateException;
        }
    }

    @Override
    public void delete(Object objeto) {
        try {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().delete(objeto);
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        } catch (HibernateException hibernateException) {
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(hibernateException);
        }
    }

    public List listaSQL(Long id) {
        try {
            List lista = null;
            Session session = HibernateUtil.getSession();
            Criteria cri = session.createCriteria(Venda.class);
            cri = cri.createCriteria("pessoa");
            cri.add(Restrictions.eq("id", id));
            lista = cri.list();
            return lista;
        } catch (HibernateException hibernateException) {
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(hibernateException);
        }
    }

    public List list(Class clazz) {
        try {
            List lista = null;
            lista = HibernateUtil.getSession().createCriteria(clazz).list();
            return lista;
        } catch (HibernateException hibernateException) {
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(hibernateException);
        }
    }

    public Object getById(Serializable id, Class clazz) {
        try {
            Object obj = HibernateUtil.getSession().get(clazz, id);
            return obj;
        } catch (HibernateException hibernateException) {
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(hibernateException);
        }
    }

    public List listaID(Class clazz, String atributo, Long id) {
        try {
            List lista = null;
            HibernateUtil.getSession().clear();
            Criteria crit = HibernateUtil.getSession().createCriteria(clazz, atributo);
            crit.add(Restrictions.eq(atributo, id));
            crit.setMaxResults(1);
            lista = crit.list();
            return lista;
        } catch (HibernateException hibernateException) {
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(hibernateException);
        }
    }

    public List listCriterio(Class clazz, String atributo, String criterio) {
        try {
            List lista = null;
            Criteria crit = HibernateUtil.getSession().createCriteria(clazz, atributo);
            crit.add(Restrictions.ilike(atributo, "%" + criterio + "%"));
            crit.addOrder(Order.asc(atributo));
            crit.setMaxResults(30);
            lista = crit.list();
            //HibernateUtil.closeSession();
            return lista;
        } catch (HibernateException hibernateException) {
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(hibernateException);
        }
    }

    public boolean listaSenha(String nome, String senha) {
        try {
            Criteria crit = HibernateUtil.getSession().createCriteria(Usuario.class);
            crit.add(Restrictions.eq("nome", nome));
            crit.add(Restrictions.eq("senha", senha));

            boolean verifica = crit.uniqueResult() != null;

            return verifica;

        } catch (HibernateException hibernateException) {
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(hibernateException);
        }
    }

    public List listaData(Class clazz, String atributo, Date dataVenda) {
        try {
            List lista = null;
            Criteria crit = HibernateUtil.getSession().createCriteria(clazz, atributo);
            crit.add(Restrictions.eq(atributo, dataVenda));
            crit.addOrder(Order.asc(atributo));
            crit.setMaxResults(30);
            lista = crit.list();
            return lista;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
