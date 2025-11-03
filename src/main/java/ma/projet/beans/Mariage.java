package ma.projet.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mariage")
@NamedQueries({
    @NamedQuery(
        name = "Femme.marieeAuMoinsDeuxFois",
        query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2"
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "Femme.nombreEnfantsEntreDates",
        query = "SELECT SUM(m.nbr_enfant) FROM mariage m WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :dateDebut AND :dateFin"
    )
})
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;
    
    @Column(name = "nbr_enfant")
    private int nbrEnfant;
    
    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;
    
    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    public Mariage() {
    }

    public Mariage(Date dateDebut, Date dateFin, int nbrEnfant, Homme homme, Femme femme) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
        this.homme = homme;
        this.femme = femme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbrEnfant() {
        return nbrEnfant;
    }

    public void setNbrEnfant(int nbrEnfant) {
        this.nbrEnfant = nbrEnfant;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }

    public boolean estEnCours() {
        return dateFin == null;
    }

    @Override
    public String toString() {
        return "Mariage{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbrEnfant=" + nbrEnfant +
                '}';
    }
}
