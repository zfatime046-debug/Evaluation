package ma.projet;

import ma.projet.entities.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestTache {

    public static void main(String[] args) throws Exception {

        TacheService        tacheService   = new TacheService();
        ProjetService       projetService  = new ProjetService();
        EmployeService      employeService = new EmployeService();
        EmployeTacheService etService      = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("========== TEST TACHE ==========\n");

        // ---- Données de base ----
        Employe e1 = new Employe("Alami",   "Ahmed", "0661234567");
        Employe e2 = new Employe("Bennani", "Sara",  "0677654321");
        employeService.create(e1);
        employeService.create(e2);

        Projet p1 = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("31/12/2013"), e1);
        Projet p2 = new Projet("Gestion RH",       sdf.parse("01/03/2013"), sdf.parse("30/11/2013"), e2);
        projetService.create(p1);
        projetService.create(p2);

        // ---- CREATE ----
        System.out.println("--- Création des tâches ---");
        Tache t1 = new Tache("Analyse",       sdf.parse("01/02/2013"), sdf.parse("28/02/2013"), 1500.0, p1);
        Tache t2 = new Tache("Conception",    sdf.parse("01/03/2013"), sdf.parse("31/03/2013"), 2000.0, p1);
        Tache t3 = new Tache("Développement", sdf.parse("01/04/2013"), sdf.parse("30/04/2013"), 3000.0, p1);
        Tache t4 = new Tache("Tests",         sdf.parse("01/05/2013"), sdf.parse("31/05/2013"),  800.0, p1);
        Tache t5 = new Tache("Recrutement",   sdf.parse("01/03/2013"), sdf.parse("30/06/2013"), 1200.0, p2);

        System.out.println("Création t1 : " + tacheService.create(t1));
        System.out.println("Création t2 : " + tacheService.create(t2));
        System.out.println("Création t3 : " + tacheService.create(t3));
        System.out.println("Création t4 : " + tacheService.create(t4));
        System.out.println("Création t5 : " + tacheService.create(t5));

        // ---- FIND ALL ----
        System.out.println("\n--- Liste de toutes les tâches ---");
        List<Tache> toutes = tacheService.findAll();
        System.out.printf("%-4s %-20s %-15s %-15s %-10s%n", "ID", "Nom", "Date Début", "Date Fin", "Prix");
        System.out.println("-".repeat(66));
        for (Tache t : toutes) {
            System.out.printf("%-4d %-20s %-15s %-15s %-10.0f DH%n",
                    t.getId(), t.getNom(),
                    sdf.format(t.getDateDebut()),
                    sdf.format(t.getDateFin()),
                    t.getPrix());
        }

        // ---- FIND BY ID ----
        System.out.println("\n--- Recherche par ID (" + t1.getId() + ") ---");
        System.out.println(tacheService.findById(t1.getId()));

        // ---- UPDATE ----
        System.out.println("\n--- Mise à jour du prix de la tâche " + t4.getId() + " ---");
        Tache toUpdate = tacheService.findById(t4.getId());
        if (toUpdate != null) {
            toUpdate.setPrix(950.0);
            System.out.println("MAJ prix : " + tacheService.update(toUpdate));
            System.out.println("Après MAJ : " + tacheService.findById(t4.getId()));
        }

        // ---- DELETE ----
        System.out.println("\n--- Suppression de la tâche " + t5.getId() + " ---");
        Tache toDelete = tacheService.findById(t5.getId());
        if (toDelete != null) {
            System.out.println("Suppression : " + tacheService.delete(toDelete));
        }

        // ---- Affectation aux employés ----
        System.out.println("\n--- Affectation des tâches aux employés ---");
        EmployeTache et1 = new EmployeTache(e1, t1, sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
        EmployeTache et2 = new EmployeTache(e1, t2, sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
        EmployeTache et3 = new EmployeTache(e1, t3, sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));
        EmployeTache et4 = new EmployeTache(e2, t4, sdf.parse("05/05/2013"), sdf.parse("28/05/2013"));
        System.out.println("ET1 : " + etService.create(et1));
        System.out.println("ET2 : " + etService.create(et2));
        System.out.println("ET3 : " + etService.create(et3));
        System.out.println("ET4 : " + etService.create(et4));

        // ---- Tâches prix > 1000 DH (requête nommée) ----
        System.out.println("\n--- Tâches dont le prix est supérieur à 1000 DH ---");
        List<Tache> tachesCheres = tacheService.findTachesPrixSuperieur1000();
        System.out.printf("%-4s %-20s %-10s%n", "ID", "Nom", "Prix");
        System.out.println("-".repeat(36));
        for (Tache t : tachesCheres) {
            System.out.printf("%-4d %-20s %-10.0f DH%n", t.getId(), t.getNom(), t.getPrix());
        }

        // ---- Tâches réalisées entre deux dates ----
        System.out.println("\n--- Tâches réalisées entre 01/02/2013 et 30/04/2013 ---");
        Date dateDebut = sdf.parse("01/02/2013");
        Date dateFin   = sdf.parse("30/04/2013");
        List<Tache> entresDates = tacheService.findTachesRealiseesentreDates(dateDebut, dateFin);
        System.out.printf("%-4s %-20s %-15s %-15s%n", "ID", "Nom", "Date Début", "Date Fin");
        System.out.println("-".repeat(56));
        for (Tache t : entresDates) {
            System.out.printf("%-4d %-20s %-15s %-15s%n",
                    t.getId(), t.getNom(),
                    sdf.format(t.getDateDebut()),
                    sdf.format(t.getDateFin()));
        }


    }
}