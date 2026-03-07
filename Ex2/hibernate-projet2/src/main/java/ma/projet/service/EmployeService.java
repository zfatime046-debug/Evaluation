package ma.projet.service;

import ma.projet.entities.Employe;
import ma.projet.entities.EmployeTache;
import ma.projet.entities.Projet;
import ma.projet.entities.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class EmployeService extends AbstractFacade<Employe> {

    public EmployeService() {
        super(Employe.class);
    }

    /**
     * Afficher la liste des tâches réalisées par un employé.
     */
    public List<Tache> findTachesParEmploye(int employeId) {
        Session session = null;
        List<Tache> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :employeId";
            list = session.createQuery(hql, Tache.class)
                          .setParameter("employeId", employeId)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findTachesParEmploye : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    /**
     * Afficher la liste des projets gérés par un employé (chef de projet).
     */
    public List<Projet> findProjetsParEmploye(int employeId) {
        Session session = null;
        List<Projet> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Projet p WHERE p.chefProjet.id = :employeId";
            list = session.createQuery(hql, Projet.class)
                          .setParameter("employeId", employeId)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findProjetsParEmploye : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return list;
    }
}
