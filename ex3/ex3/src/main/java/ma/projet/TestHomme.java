package ma.projet;

import ma.projet.entities.Femme;
import ma.projet.entities.Homme;
import ma.projet.service.HommeService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestHomme {

    public static void main(String[] args) throws Exception {

        HommeService hommeService = new HommeService();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("========== TEST HOMME ==========\n");

        // ---- CREATE ----
        System.out.println("--- Création de 5 hommes ---");
        Homme h1 = new Homme("SAFI",     "Said",    "0622000001", "Casablanca", sdf.parse("10/02/1965"));
        Homme h2 = new Homme("BENNANI",  "Karim",   "0622000002", "Rabat",      sdf.parse("05/07/1970"));
        Homme h3 = new Homme("MANSOURI", "Rachid",  "0622000003", "Fes",        sdf.parse("22/12/1968"));
        Homme h4 = new Homme("JABRI",    "Youssef", "0622000004", "Marrakech",  sdf.parse("14/04/1975"));
        Homme h5 = new Homme("BERRADA",  "Omar",    "0622000005", "Tanger",     sdf.parse("01/01/1972"));

        hommeService.create(h1); hommeService.create(h2);
        hommeService.create(h3); hommeService.create(h4);
        hommeService.create(h5);
        System.out.println("5 hommes créés.");

        // ---- FIND ALL ----
        System.out.println("\n--- Liste de tous les hommes ---");
        List<Homme> hommes = hommeService.findAll();
        System.out.printf("%-4s %-12s %-12s %-15s %-15s %-15s%n",
                "ID", "Nom", "Prénom", "Téléphone", "Adresse", "Date Naissance");
        System.out.println("-".repeat(75));
        for (Homme h : hommes) {
            System.out.printf("%-4d %-12s %-12s %-15s %-15s %-15s%n",
                    h.getId(), h.getNom(), h.getPrenom(),
                    h.getTelephone(), h.getAdresse(),
                    sdf.format(h.getDateNaissance()));
        }

        // ---- FIND BY ID ----
        System.out.println("\n--- Recherche par ID (" + h1.getId() + ") ---");
        System.out.println(hommeService.findById(h1.getId()));

        // ---- UPDATE ----
        System.out.println("\n--- Mise à jour de l'adresse de " + h1.getNom() + " ---");
        Homme toUpdate = hommeService.findById(h1.getId());
        toUpdate.setAdresse("Rabat");
        System.out.println("MAJ : " + hommeService.update(toUpdate));

        // ---- Épouses entre deux dates ----
        System.out.println("\n--- Épouses de " + h1.getNom() + " entre 01/01/1988 et 31/12/2000 ---");
        Date d1 = sdf.parse("01/01/1988");
        Date d2 = sdf.parse("31/12/2000");
        List<Femme> epouses = hommeService.findEpousesEntreDates(h1.getId(), d1, d2);
        if (epouses.isEmpty()) {
            System.out.println("  (Aucune — lancez TestMariage d'abord)");
        } else {
            epouses.forEach(f -> System.out.println("  - " + f.getNom() + " " + f.getPrenom()));
        }

        // ---- Hommes mariés à 4 femmes entre deux dates (Criteria) ----
        System.out.println("\n--- Hommes mariés à 4 femmes entre 01/01/1990 et 31/12/2010 ---");
        Date d3 = sdf.parse("01/01/1990");
        Date d4 = sdf.parse("31/12/2010");
        List<Homme> hommes4 = hommeService.findHommesMaries4FemmesEntreDates(d3, d4);
        if (hommes4.isEmpty()) {
            System.out.println("  (Aucun — lancez TestMariage d'abord)");
        } else {
            hommes4.forEach(h -> System.out.println("  - " + h.getNom() + " " + h.getPrenom()));
        }

        // ---- Détails des mariages d'un homme ----
        System.out.println("\n--- Détails des mariages de " + h1.getNom() + " ---");
        hommeService.afficherMariagesHomme(h1.getId());


    }
}