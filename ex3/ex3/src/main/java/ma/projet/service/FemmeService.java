package ma.projet.service;

import ma.projet.entities.Femme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FemmeService extends AbstractFacade<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    /**
     * Afficher la femme la plus âgée.
     */
    public Femme findFemmePlusAgee() {
        Session session = null;
        Femme femme = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Femme f WHERE f.dateNaissance = (SELECT MIN(f2.dateNaissance) FROM Femme f2)";
            femme = session.createQuery(hql, Femme.class).setMaxResults(1).uniqueResult();
        } catch (Exception e) {
            System.err.println("Erreur findFemmePlusAgee : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return femme;
    }

    /**
     * Nombre d'enfants d'une femme entre deux dates (requête native nommée).
     */
    public int getNbrEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        Session session = null;
        int total = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Object result = session.createNamedQuery("Femme.nbrEnfantsEntreDates")
                    .setParameter(1, femmeId)
                    .setParameter(2, dateDebut)
                    .setParameter(3, dateFin)
                    .getSingleResult();
            if (result != null) {
                total = ((Number) result).intValue();
            }
        } catch (Exception e) {
            System.err.println("Erreur getNbrEnfantsEntreDates : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return total;
    }

    /**
     * Femmes mariées au moins deux fois (requête nommée HQL).
     */
    public List<Femme> findFemmesMarieesDeuxFois() {
        Session session = null;
        List<Femme> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            list = session.createNamedQuery("Femme.marieesPlusDeDeux", Femme.class).list();
        } catch (Exception e) {
            System.err.println("Erreur findFemmesMarieesDeuxFois : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return list;
    }
}
