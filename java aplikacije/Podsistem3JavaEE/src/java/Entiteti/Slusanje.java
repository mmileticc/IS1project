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
public class Slusanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdSlusanja")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "KorisnikId", referencedColumnName = "IdK", nullable = false)
    private Korisnik korisnik;

    @ManyToOne(optional = false)
    @JoinColumn(name = "AudioId", referencedColumnName = "IdSnimka", nullable = false)
    private AudioSnimak audio;

    @Column(name = "PocetakVreme", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pocetakVreme;

    @Column(name = "SekundiOd", nullable = false)
    private int sekundiOd;

    @Column(name = "SekundiTrajanje", nullable = false)
    private int sekundiTrajanje;

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

    public AudioSnimak getAudio() {
        return audio;
    }

    public void setAudio(AudioSnimak audio) {
        this.audio = audio;
    }

    public Date getPocetakVreme() {
        return pocetakVreme;
    }

    public void setPocetakVreme(Date pocetakVreme) {
        this.pocetakVreme = pocetakVreme;
    }

    public int getSekundiOd() {
        return sekundiOd;
    }

    public void setSekundiOd(int sekundiOd) {
        this.sekundiOd = sekundiOd;
    }

    public int getSekundiTrajanje() {
        return sekundiTrajanje;
    }

    public void setSekundiTrajanje(int sekundiTrajanje) {
        this.sekundiTrajanje = sekundiTrajanje;
    }
}