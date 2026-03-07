package ma.projet.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_commande", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateCommande;

    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LigneCommande> lignesCommande;

    public Commande() {}

    public Commande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDateCommande() { return dateCommande; }
    public void setDateCommande(Date dateCommande) { this.dateCommande = dateCommande; }

    public List<LigneCommande> getLignesCommande() { return lignesCommande; }
    public void setLignesCommande(List<LigneCommande> lignesCommande) { this.lignesCommande = lignesCommande; }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", dateCommande=" + dateCommande + "}";
    }
}
