package ma.projet;

import ma.projet.entities.Employe;
import ma.projet.entities.Projet;
import ma.projet.entities.Tache;
import ma.projet.service.EmployeService;

import java.util.List;

public class TestEmploye {

    public static void main(String[] args) {

        EmployeService service = new EmployeService();

        System.out.println("========== TEST EMPLOYE ==========\n");

        // ---- CREATE ----
        System.out.println("--- Création des employés ---");
        Employe e1 = new Employe("Alami", "Ahmed", "0661234567");
        Employe e2 = new Employe("Bennani", "Sara",  "0677654321");
        Employe e3 = new Employe("Chraibi", "Youssef", "0655112233");

        System.out.println("Création e1 : " + service.create(e1));
        System.out.println("Création e2 : " + service.create(e2));
        System.out.println("Création e3 : " + service.create(e3));

        // ---- FIND ALL ----
        System.out.println("\n--- Liste de tous les employés ---");
        service.findAll().forEach(System.out::println);

        // ---- FIND BY ID ----
        System.out.println("\n--- Recherche par ID (1) ---");
        System.out.println(service.findById(1));

        // ---- UPDATE ----
        System.out.println("\n--- Mise à jour de l'employé 1 ---");
        Employe toUpdate = service.findById(1);
        if (toUpdate != null) {
            toUpdate.setTelephone("0699000001");
            System.out.println("MAJ : " + service.update(toUpdate));
            System.out.println("Après MAJ : " + service.findById(1));
        }

        // ---- Tâches réalisées par un employé ----
        System.out.println("\n--- Tâches réalisées par l'employé 1 ---");
        List<Tache> taches = service.findTachesParEmploye(1);
        if (taches.isEmpty()) {
            System.out.println("(Aucune tâche — lancez TestProjet d'abord pour créer les données)");
        } else {
            taches.forEach(System.out::println);
        }

        // ---- Projets gérés par un employé ----
        System.out.println("\n--- Projets gérés par l'employé 1 ---");
        List<Projet> projets = service.findProjetsParEmploye(1);
        if (projets.isEmpty()) {
            System.out.println("(Aucun projet — lancez TestProjet d'abord pour créer les données)");
        } else {
            projets.forEach(System.out::println);
        }

        System.out.println("\n========== FIN TEST EMPLOYE ==========");
    }
}
