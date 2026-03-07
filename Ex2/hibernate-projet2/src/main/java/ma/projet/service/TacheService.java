package ma.projet.service;

import ma.projet.entities.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TacheService extends AbstractFacade<Tache> {

    public TacheService() {
        super(Tache.class);
    }

    /**
     * Afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée).
     */
    public List<Tache> findTachesPrixSuperieur1000() {
        Session session = null;
        List<Tache> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            list = session.createNamedQuery("Tache.findByPrixSuperieur", Tache.class)
                          .setParameter("prix", 1000.0)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findTachesPrixSuperieur1000 : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    /**
     * Afficher les tâches réalisées entre deux dates (basé sur dateDebutReelle).
     */
    public List<Tache> findTachesRealiseesentreDates(Date dateDebut, Date dateFin) {
        Session session = null;
        List<Tache> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT DISTINCT et.tache FROM EmployeTache et " +
                         "WHERE et.dateDebutReelle BETWEEN :dateDebut AND :dateFin " +
                         "ORDER BY et.tache.nom";
            list = session.createQuery(hql, Tache.class)
                          .setParameter("dateDebut", dateDebut)
                          .setParameter("dateFin", dateFin)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findTachesRealiseesentreDates : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return list;
    }
}
