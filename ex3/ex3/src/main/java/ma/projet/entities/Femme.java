package ma.projet.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "femme")
@NamedQueries({
        @NamedQuery(
                name  = "Femme.marieesPlusDeDeux",
                query = "SELECT m.femme FROM Mariage m GROUP BY m.femme HAVING COUNT(m) >= 2"
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name  = "Femme.nbrEnfantsEntreDates",
                query = "SELECT SUM(m.nbr_enfant) FROM mariage m " +
                        "WHERE m.femme_id = ?1 " +
                        "AND m.date_debut BETWEEN ?2 AND ?3"
        )
})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Femme() {}

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }

    @Override
    public String toString() {
        return "Femme{id=" + id + ", nom='" + nom + "', prenom='" + prenom + "'}";
    }
}
