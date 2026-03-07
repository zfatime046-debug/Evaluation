package ma.projet.service;

import ma.projet.entities.EmployeTache;
import ma.projet.entities.Projet;
import ma.projet.entities.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProjetService extends AbstractFacade<Projet> {

    public ProjetService() {
        super(Projet.class);
    }

    /**
     * Afficher la liste des tâches planifiées pour un projet.
     */
    public List<Tache> findTachesPlanifieesParProjet(int projetId) {
        Session session = null;
        List<Tache> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            list = session.createNamedQuery("Tache.findByProjet", Tache.class)
                          .setParameter("projetId", projetId)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findTachesPlanifieesParProjet : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    /**
     * Afficher les tâches réalisées d'un projet avec les dates réelles (format tableau).
     */
    @SuppressWarnings("unchecked")
    public void afficherTachesRealisees(int projetId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // Récupérer le projet
            Projet projet = session.get(Projet.class, projetId);
            if (projet == null) {
                System.out.println("Projet introuvable.");
                return;
            }

            SimpleDateFormat sdfAffichage  = new SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("fr", "FR"));
            SimpleDateFormat sdfCourt      = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("Projet : " + projet.getId() +
                               "      Nom : " + projet.getNom() +
                               "     Date début : " + sdfAffichage.format(projet.getDateDebut()));
            System.out.println("Liste des tâches:");
            System.out.printf("%-4s %-20s %-22s %-20s%n",
                "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            System.out.println("-".repeat(68));

            // Récupérer les EmployeTache liées aux tâches de ce projet
            String hql = "FROM EmployeTache et WHERE et.tache.projet.id = :projetId " +
                         "ORDER BY et.dateDebutReelle ASC";
            List<EmployeTache> lignes = session.createQuery(hql, EmployeTache.class)
                                               .setParameter("projetId", projetId)
                                               .list();

            for (EmployeTache et : lignes) {
                System.out.printf("%-4d %-20s %-22s %-20s%n",
                    et.getTache().getId(),
                    et.getTache().getNom(),
                    sdfCourt.format(et.getDateDebutReelle()),
                    sdfCourt.format(et.getDateFinReelle()));
            }

        } catch (Exception e) {
            System.err.println("Erreur afficherTachesRealisees : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}
