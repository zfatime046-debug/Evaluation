package ma.projet;

import ma.projet.entities.Categorie;
import ma.projet.entities.Commande;
import ma.projet.entities.LigneCommande;
import ma.projet.entities.Produit;
import ma.projet.service.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestCommande {

    public static void main(String[] args) throws Exception {

        CategorieService categorieService   = new CategorieService();
        ProduitService    produitService     = new ProduitService();
        CommandeService   commandeService    = new CommandeService();
        LigneCommandeService lcService       = new LigneCommandeService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("========== TEST COMMANDE ==========\n");

        // ---- Données de base ----
        Categorie catInfo = new Categorie("Informatique", "Matériel informatique");
        categorieService.create(catInfo);

        Produit p1 = new Produit("ES12", "Ecran 22 pouces",  120.0, catInfo);
        Produit p2 = new Produit("ZR85", "Clavier sans fil", 100.0, catInfo);
        Produit p3 = new Produit("EE85", "Disque dur 1To",   200.0, catInfo);
        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        // ---- CREATE Commandes ----
        System.out.println("--- Création des commandes ---");
        Commande cmd1 = new Commande(sdf.parse("14/03/2013"));
        Commande cmd2 = new Commande(sdf.parse("20/05/2013"));
        Commande cmd3 = new Commande(sdf.parse("10/01/2024"));

        System.out.println("Création cmd1 : " + commandeService.create(cmd1));
        System.out.println("Création cmd2 : " + commandeService.create(cmd2));
        System.out.println("Création cmd3 : " + commandeService.create(cmd3));

        // ---- Lignes de commande ----
        System.out.println("\n--- Création des lignes de commande ---");
        LigneCommande lc1 = new LigneCommande(cmd1, p1, 7);
        LigneCommande lc2 = new LigneCommande(cmd1, p2, 14);
        LigneCommande lc3 = new LigneCommande(cmd1, p3, 5);
        LigneCommande lc4 = new LigneCommande(cmd2, p1, 3);
        LigneCommande lc5 = new LigneCommande(cmd3, p2, 10);

        System.out.println("Ligne 1 : " + lcService.create(lc1));
        System.out.println("Ligne 2 : " + lcService.create(lc2));
        System.out.println("Ligne 3 : " + lcService.create(lc3));
        System.out.println("Ligne 4 : " + lcService.create(lc4));
        System.out.println("Ligne 5 : " + lcService.create(lc5));

        // ---- FIND ALL Commandes ----
        System.out.println("\n--- Liste de toutes les commandes ---");
        commandeService.findAll().forEach(System.out::println);

        // ---- Afficher les produits d'une commande (format tableau) ----
        System.out.println("\n--- Produits de la commande " + cmd1.getId() + " ---");
        produitService.afficherProduitsParCommande(cmd1.getId());

        // ---- Produits commandés entre deux dates ----
        System.out.println("\n--- Produits commandés entre 01/01/2013 et 31/12/2013 ---");
        Date dateDebut = sdf.parse("01/01/2013");
        Date dateFin   = sdf.parse("31/12/2013");
        List<Produit> produitsDates = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
        System.out.printf("%-12s %-20s %-10s%n", "Référence", "Nom", "Prix");
        System.out.println("-".repeat(44));
        for (Produit p : produitsDates) {
            System.out.printf("%-12s %-20s %-10.0f DH%n",
                p.getReference(), p.getNom(), p.getPrix());
        }

        // ---- DELETE ----
        System.out.println("\n--- Suppression de la commande 2 ---");
        Commande toDelete = commandeService.findById(cmd2.getId());
        if (toDelete != null) {
            System.out.println("Suppression : " + commandeService.delete(toDelete));
        }

        System.out.println("\n========== FIN TEST COMMANDE ==========");
    }
}
