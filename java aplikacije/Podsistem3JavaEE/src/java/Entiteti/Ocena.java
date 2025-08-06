package Entiteti;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miletic
 */
@Entity
public class Ocena {

    @EmbeddedId
    private OcenaPK id;

    @Column(name = "Ocena", nullable = false)
    private int ocena;

    @Column(name = "DatumVreme", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;

    @ManyToOne(optional = false)
    @JoinColumn(name = "KorisnikId", referencedColumnName = "IdK", insertable = false, updatable = false)
    private Korisnik korisnik;

    @ManyToOne(optional = false)
    @JoinColumn(name = "AudioId", referencedColumnName = "IdSnimka", insertable = false, updatable = false)
    private AudioSnimak audio;

    public Ocena() {}

    public OcenaPK getId() {
        return id;
    }

    public void setId(OcenaPK id) {
        this.id = id;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
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
}