package ma.projet.service;

import ma.projet.entities.Femme;
import ma.projet.entities.Homme;
import ma.projet.entities.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HommeService extends AbstractFacade<Homme> {

    public HommeService() {
        super(Homme.class);
    }

    /**
     * Afficher les épouses d'un homme entre deux dates de mariage.
     */
    public List<Femme> findEpousesEntreDates(int hommeId, Date dateDebut, Date dateFin) {
        Session session = null;
        List<Femme> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT m.femme FROM Mariage m " +
                         "WHERE m.homme.id = :hommeId " +
                         "AND m.dateDebut BETWEEN :dateDebut AND :dateFin";
            list = session.createQuery(hql, Femme.class)
                          .setParameter("hommeId", hommeId)
                          .setParameter("dateDebut", dateDebut)
                          .setParameter("dateFin", dateFin)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findEpousesEntreDates : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return list;
    }

    /**
     * Afficher les hommes mariés à quatre femmes entre deux dates (API Criteria).
     */
    public List<Homme> findHommesMaries4FemmesEntreDates(Date dateDebut, Date dateFin) {
        Session session = null;
        List<Homme> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Homme> cq = cb.createQuery(Homme.class);
            Root<Mariage> root = cq.from(Mariage.class);

            // Jointure vers Homme
            Join<Mariage, Homme> hommeJoin = root.join("homme");

            // Filtrer par dates
            Predicate datePredicate = cb.between(root.get("dateDebut"), dateDebut, dateFin);

            // Grouper par homme, HAVING COUNT >= 4
            cq.select(hommeJoin)
              .where(datePredicate)
              .groupBy(hommeJoin.get("id"))
              .having(cb.ge(cb.count(root), 4L));

            list = session.createQuery(cq).list();
        } catch (Exception e) {
            System.err.println("Erreur findHommesMaries4FemmesEntreDates : " + e.getMessage());
        } finally { if (session != null) session.close(); }
        return list;
    }

    /**
     * Afficher les mariages d'un homme avec tous les détails (format attendu).
     */
    public void afficherMariagesHomme(int hommeId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) { System.out.println("Homme introuvable."); return; }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("Nom : " + homme.getNom().toUpperCase() + " " + homme.getPrenom().toUpperCase());

            // Mariages en cours (dateFin == null)
            String hqlEnCours = "FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NULL ORDER BY m.dateDebut";
            List<Mariage> enCours = session.createQuery(hqlEnCours, Mariage.class)
                                           .setParameter("hommeId", hommeId).list();

            System.out.println("Mariages En Cours :");
            int i = 1;
            for (Mariage m : enCours) {
                System.out.printf("%d. Femme : %-20s Date Début : %-15s Nbr Enfants : %d%n",
                    i++,
                    (m.getFemme().getNom() + " " + m.getFemme().getPrenom()).toUpperCase(),
                    sdf.format(m.getDateDebut()),
                    m.getNbrEnfant());
            }

            // Mariages échoués (dateFin != null)
            String hqlEchoues = "FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NOT NULL ORDER BY m.dateDebut";
            List<Mariage> echoues = session.createQuery(hqlEchoues, Mariage.class)
                                           .setParameter("hommeId", hommeId).list();

            System.out.println("\nMariages échoués :");
            i = 1;
            for (Mariage m : echoues) {
                System.out.printf("%d. Femme : %-20s Date Début : %-15s%n",
                    i++,
                    (m.getFemme().getNom() + " " + m.getFemme().getPrenom()).toUpperCase(),
                    sdf.format(m.getDateDebut()));
                System.out.printf("   Date Fin : %-18s Nbr Enfants : %d%n",
                    sdf.format(m.getDateFin()),
                    m.getNbrEnfant());
            }

        } catch (Exception e) {
            System.err.println("Erreur afficherMariagesHomme : " + e.getMessage());
        } finally { if (session != null) session.close(); }
    }
}
