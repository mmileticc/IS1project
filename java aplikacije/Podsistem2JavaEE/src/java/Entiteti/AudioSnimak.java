package Entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "VlasnikId", referencedColumnName = "IdK", nullable = false)
    private Korisnik vlasnik;
    
    @OneToMany(mappedBy = "audioSnimak", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AudioSnimakKategorija> kategorije;

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

    public Korisnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Korisnik vlasnik) {
        this.vlasnik = vlasnik;
    }
    public List<AudioSnimakKategorija> getKategorije() {
    return kategorije;
    }

    public void setKategorije(List<AudioSnimakKategorija> kategorije) {
        this.kategorije = kategorije;
    }
}