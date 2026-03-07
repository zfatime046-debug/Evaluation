package ma.projet.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
@NamedQueries({
    @NamedQuery(
        name = "Produit.findByPrixSuperieur",
        query = "SELECT p FROM Produit p WHERE p.prix > :prix ORDER BY p.prix ASC"
    ),
    @NamedQuery(
        name = "Produit.findByCategorie",
        query = "SELECT p FROM Produit p WHERE p.categorie.id = :categorieId"
    )
})
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prix", nullable = false)
    private double prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande;

    public Produit() {}

    public Produit(String reference, String nom, double prix, Categorie categorie) {
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public List<LigneCommande> getLignesCommande() { return lignesCommande; }
    public void setLignesCommande(List<LigneCommande> lignesCommande) { this.lignesCommande = lignesCommande; }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", reference='" + reference + "', nom='" + nom + "', prix=" + prix + "}";
    }
}
