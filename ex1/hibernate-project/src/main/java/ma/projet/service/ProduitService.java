package ma.projet.service;

import ma.projet.entities.LigneCommande;
import ma.projet.entities.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProduitService extends AbstractFacade<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    /**
     * Retourne la liste des produits appartenant à une catégorie donnée.
     */
    @SuppressWarnings("unchecked")
    public List<Produit> findByCategorie(int categorieId) {
        Session session = null;
        List<Produit> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            list = session.createNamedQuery("Produit.findByCategorie", Produit.class)
                          .setParameter("categorieId", categorieId)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findByCategorie : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    /**
     * Retourne les produits commandés entre deux dates.
     */
    @SuppressWarnings("unchecked")
    public List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        Session session = null;
        List<Produit> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT DISTINCT lc.produit FROM LigneCommande lc " +
                         "WHERE lc.commande.dateCommande BETWEEN :dateDebut AND :dateFin " +
                         "ORDER BY lc.produit.nom";
            list = session.createQuery(hql, Produit.class)
                          .setParameter("dateDebut", dateDebut)
                          .setParameter("dateFin", dateFin)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findProduitsCommandesEntreDates : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    /**
     * Affiche les produits commandés dans une commande donnée (format tableau).
     */
    @SuppressWarnings("unchecked")
    public void afficherProduitsParCommande(int commandeId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // Récupérer la commande
            String hqlCommande = "FROM Commande c WHERE c.id = :commandeId";
            ma.projet.entities.Commande commande = (ma.projet.entities.Commande)
                session.createQuery(hqlCommande)
                       .setParameter("commandeId", commandeId)
                       .uniqueResult();

            if (commande == null) {
                System.out.println("Commande introuvable.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("fr", "FR"));
            System.out.println("Commande : " + commande.getId() +
                               "     Date : " + sdf.format(commande.getDateCommande()));
            System.out.println("Liste des produits :");
            System.out.printf("%-12s %-10s %-10s%n", "Référence", "Prix", "Quantité");

            // Récupérer les lignes de la commande
            String hqlLignes = "FROM LigneCommande lc WHERE lc.commande.id = :commandeId";
            List<LigneCommande> lignes = session.createQuery(hqlLignes, LigneCommande.class)
                                                .setParameter("commandeId", commandeId)
                                                .list();

            for (LigneCommande lc : lignes) {
                System.out.printf("%-12s %-10s %-10d%n",
                    lc.getProduit().getReference(),
                    (int) lc.getProduit().getPrix() + " DH",
                    lc.getQuantite());
            }

        } catch (Exception e) {
            System.err.println("Erreur afficherProduitsParCommande : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    /**
     * Retourne les produits dont le prix est supérieur à 100 DH (requête nommée).
     */
    public List<Produit> findProduitsPrixSuperieur100() {
        Session session = null;
        List<Produit> list = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            list = session.createNamedQuery("Produit.findByPrixSuperieur", Produit.class)
                          .setParameter("prix", 100.0)
                          .list();
        } catch (Exception e) {
            System.err.println("Erreur findProduitsPrixSuperieur100 : " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
        return list;
    }
}
