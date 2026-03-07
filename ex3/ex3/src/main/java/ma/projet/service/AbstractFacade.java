package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFacade<T> implements IDao<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public boolean create(T o) {
        Session session = null; Transaction tx = null; boolean etat = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit(); etat = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur create : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return etat;
    }

    @Override
    public boolean update(T o) {
        Session session = null; Transaction tx = null; boolean etat = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit(); etat = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur update : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return etat;
    }

    @Override
    public boolean delete(T o) {
        Session session = null; Transaction tx = null; boolean etat = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit(); etat = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur delete : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return etat;
    }

    @Override
    public T findById(int id) {
        Session session = null; T entity = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            entity = session.get(entityClass, id);
        } catch (HibernateException e) {
            System.err.println("Erreur findById : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return entity;
    }

    @Override
    public List<T> findAll() {
        Session session = null; List<T> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            list = session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } catch (HibernateException e) {
            System.err.println("Erreur findAll : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return list;
    }
}
