package Entiteti;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miletic
 */
@Entity
public class AudioSnimak {

    @Id()
    @Basic(optional = false)
    @Column(name = "IdSnimka")
    private int idSnimka;

    @Column(name = "Naziv", nullable = false, length = 100)
    private String naziv;

    @Column(name = "Trajanje", nullable = false)
    private int trajanje; // Trajanje u sekundama

    @Column(name = "DatumPostavljanja", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumPostavljanja;

    @Column(name = "vlasnikId", nullable = false)
    private int vlasnikId;
    
    @OneToMany(mappedBy = "audio", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ocena> ocene;

    @OneToMany(mappedBy = "audio", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Omiljeni> omiljeni;
    
    @OneToMany(mappedBy = "audio", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Slusanje> slusanja;

    public int getIdSnimka() {
        return idSnimka;
    }

    public void setIdSnimka(int idSnimka) {
        this.idSnimka = idSnimka;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumPostavljanja() {
        return datumPostavljanja;
    }

    public void setDatumPostavljanja(Date datumPostavljanja) {
        this.datumPostavljanja = datumPostavljanja;
    }

    public int getVlasnikId() {
        return vlasnikId;
    }

    public void setVlasnikId(int vlasnikId) {
        this.vlasnikId = vlasnikId;
    }
}