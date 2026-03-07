package ma.projet;

import ma.projet.entities.Categorie;
import ma.projet.entities.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.ProduitService;

import java.util.List;

public class TestProduit {

    public static void main(String[] args) {

        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();

        System.out.println("========== TEST PRODUIT ==========\n");

        // ---- Préparation des catégories ----
        Categorie catInfo = new Categorie("Informatique", "Matériel informatique");
        Categorie catElec = new Categorie("Electronique", "Appareils électroniques");
        categorieService.create(catInfo);
        categorieService.create(catElec);

        // ---- CREATE ----
        System.out.println("--- Création des produits ---");
        Produit p1 = new Produit("ES12", "Ecran 22 pouces", 120.0, catInfo);
        Produit p2 = new Produit("ZR85", "Clavier sans fil", 100.0, catInfo);
        Produit p3 = new Produit("EE85", "Disque dur 1To", 200.0, catInfo);
        Produit p4 = new Produit("TV55", "Télévision 55 pouces", 85.0, catElec);
        Produit p5 = new Produit("SP10", "Enceinte bluetooth", 150.0, catElec);

        System.out.println("Création p1 : " + produitService.create(p1));
        System.out.println("Création p2 : " + produitService.create(p2));
        System.out.println("Création p3 : " + produitService.create(p3));
        System.out.println("Création p4 : " + produitService.create(p4));
        System.out.println("Création p5 : " + produitService.create(p5));

        // ---- FIND ALL ----
        System.out.println("\n--- Liste de tous les produits ---");
        produitService.findAll().forEach(System.out::println);

        // ---- FIND BY ID ----
        System.out.println("\n--- Recherche par ID (1) ---");
        System.out.println(produitService.findById(1));

        // ---- UPDATE ----
        System.out.println("\n--- Mise à jour du produit 1 ---");
        Produit toUpdate = produitService.findById(1);
        if (toUpdate != null) {
            toUpdate.setPrix(130.0);
            System.out.println("Mise à jour prix : " + produitService.update(toUpdate));
        }

        // ---- FIND BY CATEGORIE ----
        System.out.println("\n--- Produits de la catégorie Informatique (id=" + catInfo.getId() + ") ---");
        List<Produit> parCategorie = produitService.findByCategorie(catInfo.getId());
        parCategorie.forEach(System.out::println);

        // ---- FIND PRIX > 100 (requête nommée) ----
        System.out.println("\n--- Produits dont le prix est supérieur à 100 DH ---");
        List<Produit> prixSup = produitService.findProduitsPrixSuperieur100();
        System.out.printf("%-12s %-20s %-10s%n", "Référence", "Nom", "Prix");
        System.out.println("-".repeat(44));
        for (Produit p : prixSup) {
            System.out.printf("%-12s %-20s %-10.0f DH%n",
                p.getReference(), p.getNom(), p.getPrix());
        }

        System.out.println("\n========== FIN TEST PRODUIT ==========");
    }
}
