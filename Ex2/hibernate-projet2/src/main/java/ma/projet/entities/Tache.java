package ma.projet.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tache")
@NamedQueries({
        @NamedQuery(
                name = "Tache.findByPrixSuperieur",
                query = "SELECT t FROM Tache t WHERE t.prix > :prix ORDER BY t.prix ASC"
        ),
        @NamedQuery(
                name = "Tache.findByProjet",
                query = "SELECT t FROM Tache t WHERE t.projet.id = :projetId ORDER BY t.dateDebut ASC"
        )
})
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "prix")
    private double prix;

    // ✅ Relation vers Projet (manquait)
    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    // ✅ Relation vers EmployeTache (manquait)
    @OneToMany(mappedBy = "tache", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeTache> employeTaches;

    public Tache() {}

    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }

    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }

    public List<EmployeTache> getEmployeTaches() { return employeTaches; }
    public void setEmployeTaches(List<EmployeTache> employeTaches) { this.employeTaches = employeTaches; }

    @Override
    public String toString() {
        return "Tache{id=" + id + ", nom='" + nom + "', prix=" + prix + "}";
    }
}