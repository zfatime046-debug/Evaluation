package ma.projet;

import ma.projet.entities.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestMariage {

    public static void main(String[] args) throws Exception {

        FemmeService   femmeService   = new FemmeService();
        HommeService   hommeService   = new HommeService();
        MariageService mariageService = new MariageService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("========== TEST MARIAGE ==========\n");

        // ================================================================
        // 1. Créer 10 femmes
        // ================================================================
        System.out.println("--- Création de 10 femmes ---");
        Femme f1  = new Femme("RAMI",   "Salima",  "0611000001", "Casablanca", sdf.parse("03/05/1970"));
        Femme f2  = new Femme("ALI",    "Amal",    "0611000002", "Rabat",      sdf.parse("15/08/1975"));
        Femme f3  = new Femme("ALAOUI", "Wafa",    "0611000003", "Fes",        sdf.parse("04/11/1980"));
        Femme f4  = new Femme("ALAMI",  "Karima",  "0611000004", "Marrakech",  sdf.parse("03/09/1968"));
        Femme f5  = new Femme("IDRISSI","Nadia",   "0611000005", "Agadir",     sdf.parse("20/01/1985"));
        Femme f6  = new Femme("BAHI",   "Fatima",  "0611000006", "Tanger",     sdf.parse("12/06/1972"));
        Femme f7  = new Femme("CHRAIBI","Samira",  "0611000007", "Meknes",     sdf.parse("07/03/1978"));
        Femme f8  = new Femme("TAZI",   "Houda",   "0611000008", "Oujda",      sdf.parse("25/09/1982"));
        Femme f9  = new Femme("BENALI", "Zineb",   "0611000009", "Kenitra",    sdf.parse("11/11/1990"));
        Femme f10 = new Femme("FILALI", "Loubna",  "0611000010", "Safi",       sdf.parse("30/04/1988"));

        femmeService.create(f1);  femmeService.create(f2);
        femmeService.create(f3);  femmeService.create(f4);
        femmeService.create(f5);  femmeService.create(f6);
        femmeService.create(f7);  femmeService.create(f8);
        femmeService.create(f9);  femmeService.create(f10);
        System.out.println("10 femmes créées avec succès.");

        // ================================================================
        // 2. Créer 5 hommes
        // ================================================================
        System.out.println("\n--- Création de 5 hommes ---");
        Homme h1 = new Homme("SAFI",    "Said",    "0622000001", "Casablanca", sdf.parse("10/02/1965"));
        Homme h2 = new Homme("BENNANI", "Karim",   "0622000002", "Rabat",      sdf.parse("05/07/1970"));
        Homme h3 = new Homme("MANSOURI","Rachid",  "0622000003", "Fes",        sdf.parse("22/12/1968"));
        Homme h4 = new Homme("JABRI",   "Youssef", "0622000004", "Marrakech",  sdf.parse("14/04/1975"));
        Homme h5 = new Homme("BERRADA", "Omar",    "0622000005", "Tanger",     sdf.parse("01/01/1972"));

        hommeService.create(h1); hommeService.create(h2);
        hommeService.create(h3); hommeService.create(h4);
        hommeService.create(h5);
        System.out.println("5 hommes créés avec succès.");

        // ================================================================
        // 3. Créer les mariages
        // ================================================================
        System.out.println("\n--- Création des mariages ---");

        // h1 (SAFI Said) : 3 mariages en cours + 1 échoué
        mariageService.create(new Mariage(h1, f4, sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0)); // échoué
        mariageService.create(new Mariage(h1, f1, sdf.parse("03/09/1990"), null, 4)); // en cours
        mariageService.create(new Mariage(h1, f2, sdf.parse("03/09/1995"), null, 2)); // en cours
        mariageService.create(new Mariage(h1, f3, sdf.parse("04/11/2000"), null, 3)); // en cours

        // h2 (BENNANI Karim) : 2 mariages
        mariageService.create(new Mariage(h2, f5, sdf.parse("10/05/1998"), null, 2));
        mariageService.create(new Mariage(h2, f6, sdf.parse("20/06/2005"), null, 1));

        // h3 (MANSOURI Rachid) : 2 mariages (femme mariée 2x)
        mariageService.create(new Mariage(h3, f1, sdf.parse("01/01/1988"), sdf.parse("01/01/1990"), 1)); // f1 mariée 2x
        mariageService.create(new Mariage(h3, f7, sdf.parse("15/03/2002"), null, 3));

        // h4 (JABRI Youssef) : 4 mariages entre 1995-2010 → pour test Criteria
        mariageService.create(new Mariage(h4, f5, sdf.parse("01/01/1996"), sdf.parse("01/01/2000"), 0));
        mariageService.create(new Mariage(h4, f8, sdf.parse("05/05/1998"), null, 2));
        mariageService.create(new Mariage(h4, f9, sdf.parse("10/10/2003"), null, 1));
        mariageService.create(new Mariage(h4, f10,sdf.parse("20/02/2007"), null, 2));

        // h5 (BERRADA Omar) : 1 mariage
        mariageService.create(new Mariage(h5, f6, sdf.parse("12/12/1999"), sdf.parse("12/12/2003"), 2));

        System.out.println("Mariages créés avec succès.");

        // ================================================================
        // 4. Afficher la liste des femmes
        // ================================================================
        System.out.println("\n--- Liste de toutes les femmes ---");
        List<Femme> femmes = femmeService.findAll();
        System.out.printf("%-4s %-15s %-12s %-15s %-15s%n", "ID", "Nom", "Prénom", "Téléphone", "Date Naissance");
        System.out.println("-".repeat(63));
        for (Femme f : femmes) {
            System.out.printf("%-4d %-15s %-12s %-15s %-15s%n",
                f.getId(), f.getNom(), f.getPrenom(), f.getTelephone(),
                sdf.format(f.getDateNaissance()));
        }

        // ================================================================
        // 5. Afficher la femme la plus âgée
        // ================================================================
        System.out.println("\n--- Femme la plus âgée ---");
        Femme plusAgee = femmeService.findFemmePlusAgee();
        if (plusAgee != null) {
            System.out.println("Nom    : " + plusAgee.getNom() + " " + plusAgee.getPrenom());
            System.out.println("Né(e)  : " + sdf.format(plusAgee.getDateNaissance()));
            System.out.println("Tél    : " + plusAgee.getTelephone());
            System.out.println("Adresse: " + plusAgee.getAdresse());
        }

        // ================================================================
        // 6. Afficher les épouses d'un homme donné entre deux dates
        // ================================================================
        System.out.println("\n--- Épouses de " + h1.getNom() + " " + h1.getPrenom() +
                           " entre 01/01/1988 et 31/12/2000 ---");
        Date d1 = sdf.parse("01/01/1988");
        Date d2 = sdf.parse("31/12/2000");
        List<Femme> epouses = hommeService.findEpousesEntreDates(h1.getId(), d1, d2);
        epouses.forEach(f -> System.out.println("  - " + f.getNom() + " " + f.getPrenom()));

        // ================================================================
        // 7. Afficher le nombre d'enfants d'une femme entre deux dates
        // ================================================================
        System.out.println("\n--- Nombre d'enfants de " + f1.getNom() + " " + f1.getPrenom() +
                           " entre 01/01/1985 et 31/12/2005 ---");
        Date d3 = sdf.parse("01/01/1985");
        Date d4 = sdf.parse("31/12/2005");
        int nbrEnfants = femmeService.getNbrEnfantsEntreDates(f1.getId(), d3, d4);
        System.out.println("Nombre total d'enfants : " + nbrEnfants);

        // ================================================================
        // 8. Afficher les femmes mariées deux fois ou plus
        // ================================================================
        System.out.println("\n--- Femmes mariées au moins 2 fois ---");
        List<Femme> deuxFois = femmeService.findFemmesMarieesDeuxFois();
        if (deuxFois.isEmpty()) {
            System.out.println("  Aucune femme mariée deux fois.");
        } else {
            deuxFois.forEach(f -> System.out.println("  - " + f.getNom() + " " + f.getPrenom()));
        }

        // ================================================================
        // 9. Afficher les hommes mariés à 4 femmes entre deux dates (Criteria)
        // ================================================================
        System.out.println("\n--- Hommes mariés à 4 femmes entre 01/01/1990 et 31/12/2010 ---");
        Date d5 = sdf.parse("01/01/1990");
        Date d6 = sdf.parse("31/12/2010");
        List<Homme> hommes4 = hommeService.findHommesMaries4FemmesEntreDates(d5, d6);
        if (hommes4.isEmpty()) {
            System.out.println("  Aucun homme marié à 4 femmes dans cette période.");
        } else {
            hommes4.forEach(h -> System.out.println("  - " + h.getNom() + " " + h.getPrenom()));
        }

        // ================================================================
        // 10. Afficher les mariages d'un homme avec tous les détails
        // ================================================================
        System.out.println("\n--- Détails des mariages de " + h1.getNom() + " " + h1.getPrenom() + " ---");
        hommeService.afficherMariagesHomme(h1.getId());

    }
}
