package ma.projet;

import ma.projet.entities.*;
import ma.projet.service.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestProjet {

    public static void main(String[] args) throws Exception {

        EmployeService      employeService      = new EmployeService();
        ProjetService       projetService       = new ProjetService();
        TacheService        tacheService        = new TacheService();
        EmployeTacheService etService           = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("========== TEST PROJET ==========\n");

        // ---- Employés ----
        Employe e1 = new Employe("Alami",   "Ahmed",   "0661234567");
        Employe e2 = new Employe("Bennani", "Sara",    "0677654321");
        employeService.create(e1);
        employeService.create(e2);

        // ---- Projets ----
        System.out.println("--- Création des projets ---");
        Projet p1 = new Projet("Gestion de stock",    sdf.parse("14/01/2013"), sdf.parse("31/12/2013"), e1);
        Projet p2 = new Projet("Gestion RH",          sdf.parse("01/03/2013"), sdf.parse("30/11/2013"), e2);
        System.out.println("Création p1 : " + projetService.create(p1));
        System.out.println("Création p2 : " + projetService.create(p2));

        // ---- Tâches ----
        System.out.println("\n--- Création des tâches ---");
        Tache t1 = new Tache("Analyse",       sdf.parse("01/02/2013"), sdf.parse("28/02/2013"), 1500.0, p1);
        Tache t2 = new Tache("Conception",    sdf.parse("01/03/2013"), sdf.parse("31/03/2013"), 2000.0, p1);
        Tache t3 = new Tache("Développement", sdf.parse("01/04/2013"), sdf.parse("30/04/2013"), 3000.0, p1);
        Tache t4 = new Tache("Tests",         sdf.parse("01/05/2013"), sdf.parse("31/05/2013"),  800.0, p1);
        Tache t5 = new Tache("Recrutement",   sdf.parse("01/03/2013"), sdf.parse("30/06/2013"), 1200.0, p2);
        System.out.println("Tâche t1 : " + tacheService.create(t1));
        System.out.println("Tâche t2 : " + tacheService.create(t2));
        System.out.println("Tâche t3 : " + tacheService.create(t3));
        System.out.println("Tâche t4 : " + tacheService.create(t4));
        System.out.println("Tâche t5 : " + tacheService.create(t5));

        // ---- EmployeTache (réalisations) ----
        System.out.println("\n--- Affectation des tâches aux employés ---");
        EmployeTache et1 = new EmployeTache(e1, t1, sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
        EmployeTache et2 = new EmployeTache(e1, t2, sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
        EmployeTache et3 = new EmployeTache(e1, t3, sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));
        EmployeTache et4 = new EmployeTache(e2, t4, sdf.parse("05/05/2013"), sdf.parse("28/05/2013"));
        EmployeTache et5 = new EmployeTache(e2, t5, sdf.parse("05/03/2013"), sdf.parse("25/06/2013"));
        System.out.println("ET1 : " + etService.create(et1));
        System.out.println("ET2 : " + etService.create(et2));
        System.out.println("ET3 : " + etService.create(et3));
        System.out.println("ET4 : " + etService.create(et4));
        System.out.println("ET5 : " + etService.create(et5));

        // ---- Liste de tous les projets ----
        System.out.println("\n--- Liste de tous les projets ---");
        projetService.findAll().forEach(System.out::println);

        // ---- Tâches planifiées d'un projet ----
        System.out.println("\n--- Tâches planifiées du projet " + p1.getId() + " ---");
        List<Tache> planifiees = projetService.findTachesPlanifieesParProjet(p1.getId());
        System.out.printf("%-4s %-20s %-15s %-15s %-10s%n", "ID", "Nom", "Date Début", "Date Fin", "Prix");
        System.out.println("-".repeat(66));
        for (Tache t : planifiees) {
            System.out.printf("%-4d %-20s %-15s %-15s %-10.0f DH%n",
                t.getId(), t.getNom(),
                sdf.format(t.getDateDebut()),
                sdf.format(t.getDateFin()),
                t.getPrix());
        }

        // ---- Tâches réalisées avec dates réelles (format attendu) ----
        System.out.println("\n--- Tâches réalisées du projet " + p1.getId() + " ---");
        projetService.afficherTachesRealisees(p1.getId());

        // ---- Tâches prix > 1000 (requête nommée) ----
        System.out.println("\n--- Tâches dont le prix est supérieur à 1000 DH ---");
        List<Tache> cheres = tacheService.findTachesPrixSuperieur1000();
        System.out.printf("%-4s %-20s %-10s%n", "ID", "Nom", "Prix");
        System.out.println("-".repeat(36));
        for (Tache t : cheres) {
            System.out.printf("%-4d %-20s %-10.0f DH%n", t.getId(), t.getNom(), t.getPrix());
        }

        // ---- Tâches réalisées entre deux dates ----
        System.out.println("\n--- Tâches réalisées entre 01/02/2013 et 30/04/2013 ---");
        Date d1 = sdf.parse("01/02/2013");
        Date d2 = sdf.parse("30/04/2013");
        List<Tache> entresDates = tacheService.findTachesRealiseesentreDates(d1, d2);
        entresDates.forEach(System.out::println);

        // ---- Tâches et projets par employé ----
        System.out.println("\n--- Tâches réalisées par l'employé " + e1.getId() + " ---");
        employeService.findTachesParEmploye(e1.getId()).forEach(System.out::println);

        System.out.println("\n--- Projets gérés par l'employé " + e1.getId() + " ---");
        employeService.findProjetsParEmploye(e1.getId()).forEach(System.out::println);


    }
}
