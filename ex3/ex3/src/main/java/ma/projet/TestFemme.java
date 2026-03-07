package ma.projet;

import ma.projet.entities.Femme;
import ma.projet.service.FemmeService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestFemme {

    public static void main(String[] args) throws Exception {

        FemmeService femmeService = new FemmeService();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("========== TEST FEMME ==========\n");

        // ---- CREATE ----
        System.out.println("--- Création de 10 femmes ---");
        Femme f1  = new Femme("RAMI",    "Salima",  "0611000001", "Casablanca", sdf.parse("03/05/1970"));
        Femme f2  = new Femme("ALI",     "Amal",    "0611000002", "Rabat",      sdf.parse("15/08/1975"));
        Femme f3  = new Femme("ALAOUI",  "Wafa",    "0611000003", "Fes",        sdf.parse("04/11/1980"));
        Femme f4  = new Femme("ALAMI",   "Karima",  "0611000004", "Marrakech",  sdf.parse("03/09/1968"));
        Femme f5  = new Femme("IDRISSI", "Nadia",   "0611000005", "Agadir",     sdf.parse("20/01/1985"));
        Femme f6  = new Femme("BAHI",    "Fatima",  "0611000006", "Tanger",     sdf.parse("12/06/1972"));
        Femme f7  = new Femme("CHRAIBI", "Samira",  "0611000007", "Meknes",     sdf.parse("07/03/1978"));
        Femme f8  = new Femme("TAZI",    "Houda",   "0611000008", "Oujda",      sdf.parse("25/09/1982"));
        Femme f9  = new Femme("BENALI",  "Zineb",   "0611000009", "Kenitra",    sdf.parse("11/11/1990"));
        Femme f10 = new Femme("FILALI",  "Loubna",  "0611000010", "Safi",       sdf.parse("30/04/1988"));

        femmeService.create(f1);  femmeService.create(f2);
        femmeService.create(f3);  femmeService.create(f4);
        femmeService.create(f5);  femmeService.create(f6);
        femmeService.create(f7);  femmeService.create(f8);
        femmeService.create(f9);  femmeService.create(f10);
        System.out.println("10 femmes créées.");

        // ---- FIND ALL ----
        System.out.println("\n--- Liste de toutes les femmes ---");
        List<Femme> femmes = femmeService.findAll();
        System.out.printf("%-4s %-12s %-12s %-15s %-15s %-15s%n",
                "ID", "Nom", "Prénom", "Téléphone", "Adresse", "Date Naissance");
        System.out.println("-".repeat(75));
        for (Femme f : femmes) {
            System.out.printf("%-4d %-12s %-12s %-15s %-15s %-15s%n",
                    f.getId(), f.getNom(), f.getPrenom(),
                    f.getTelephone(), f.getAdresse(),
                    sdf.format(f.getDateNaissance()));
        }

        // ---- FIND BY ID ----
        System.out.println("\n--- Recherche par ID (" + f1.getId() + ") ---");
        Femme found = femmeService.findById(f1.getId());
        System.out.println(found);

        // ---- UPDATE ----
        System.out.println("\n--- Mise à jour du téléphone de " + f1.getNom() + " ---");
        found.setTelephone("0699999999");
        System.out.println("MAJ : " + femmeService.update(found));
        System.out.println("Après MAJ : " + femmeService.findById(f1.getId()));

        // ---- Femme la plus âgée ----
        System.out.println("\n--- Femme la plus âgée ---");
        Femme plusAgee = femmeService.findFemmePlusAgee();
        if (plusAgee != null) {
            System.out.println("Nom     : " + plusAgee.getNom() + " " + plusAgee.getPrenom());
            System.out.println("Née le  : " + sdf.format(plusAgee.getDateNaissance()));
            System.out.println("Adresse : " + plusAgee.getAdresse());
        }

        // ---- Nombre d'enfants entre deux dates ----
        System.out.println("\n--- Nombre d'enfants de " + f1.getNom() +
                " entre 01/01/1985 et 31/12/2005 ---");
        Date d1 = sdf.parse("01/01/1985");
        Date d2 = sdf.parse("31/12/2005");
        int nbr = femmeService.getNbrEnfantsEntreDates(f1.getId(), d1, d2);
        System.out.println("Nombre d'enfants : " + nbr);

        // ---- Femmes mariées au moins 2 fois ----
        System.out.println("\n--- Femmes mariées au moins 2 fois ---");
        List<Femme> deuxFois = femmeService.findFemmesMarieesDeuxFois();
        if (deuxFois.isEmpty()) {
            System.out.println("  (Aucune — lancez TestMariage d'abord pour créer les mariages)");
        } else {
            deuxFois.forEach(f -> System.out.println("  - " + f.getNom() + " " + f.getPrenom()));
        }

        // ---- DELETE ----
        System.out.println("\n--- Suppression de " + f10.getNom() + " " + f10.getPrenom() + " ---");
        Femme toDelete = femmeService.findById(f10.getId());
        if (toDelete != null) {
            System.out.println("Suppression : " + femmeService.delete(toDelete));
        }
        System.out.println("Nombre de femmes après suppression : " + femmeService.findAll().size());

    }
}