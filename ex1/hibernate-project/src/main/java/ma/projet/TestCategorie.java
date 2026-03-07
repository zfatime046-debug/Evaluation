package ma.projet;

import ma.projet.entities.Categorie;
import ma.projet.service.CategorieService;

import java.util.List;

public class TestCategorie {

    public static void main(String[] args) {

        CategorieService service = new CategorieService();

        System.out.println("========== TEST CATEGORIE ==========\n");

        // ---- CREATE ----
        System.out.println("--- Création des catégories ---");
        Categorie c1 = new Categorie("Informatique", "Matériel et accessoires informatiques");
        Categorie c2 = new Categorie("Electronique", "Appareils électroniques");
        Categorie c3 = new Categorie("Téléphonie", "Smartphones et accessoires");

        System.out.println("Création c1 : " + service.create(c1));
        System.out.println("Création c2 : " + service.create(c2));
        System.out.println("Création c3 : " + service.create(c3));

        // ---- FIND ALL ----
        System.out.println("\n--- Liste de toutes les catégories ---");
        List<Categorie> categories = service.findAll();
        for (Categorie c : categories) {
            System.out.println(c);
        }

        // ---- FIND BY ID ----
        System.out.println("\n--- Recherche par ID (1) ---");
        Categorie found = service.findById(1);
        System.out.println(found);

        // ---- UPDATE ----
        System.out.println("\n--- Mise à jour de la catégorie 1 ---");
        if (found != null) {
            found.setDescription("Ordinateurs, périphériques et logiciels");
            System.out.println("Mise à jour : " + service.update(found));
            System.out.println("Après MAJ : " + service.findById(1));
        }

        // ---- DELETE ----
        System.out.println("\n--- Suppression de la catégorie 3 ---");
        Categorie toDelete = service.findById(3);
        if (toDelete != null) {
            System.out.println("Suppression : " + service.delete(toDelete));
        }

        System.out.println("\n--- Liste après suppression ---");
        service.findAll().forEach(System.out::println);

        System.out.println("\n========== FIN TEST CATEGORIE ==========");
    }
}
