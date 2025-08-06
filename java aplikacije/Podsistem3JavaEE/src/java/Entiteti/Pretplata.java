package Entiteti;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miletic
 */
@Entity
public class Pretplata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPretplate")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "KorisnikId", referencedColumnName = "IdK", nullable = false)
    private Korisnik korisnik;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PaketId", referencedColumnName = "IdPaketa", nullable = false)
    private Paket paket;

    @Column(name = "DatumPocetka", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumPocetka;

    @Column(name = "Cena", nullable = false)
    private double cena;

    // Getteri i setteri

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Paket getPaket() {
        return paket;
    }

    public void setPaket(Paket paket) {
        this.paket = paket;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
}