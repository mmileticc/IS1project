package Entiteti;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Miletic
 */
@Entity
public class Omiljeni {

    @EmbeddedId
    private OmiljeniPK id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "KorisnikId", referencedColumnName = "IdK", insertable = false, updatable = false)
    private Korisnik korisnik;

    @ManyToOne(optional = false)
    @JoinColumn(name = "AudioId", referencedColumnName = "IdSnimka", insertable = false, updatable = false)
    private AudioSnimak audio;

    public Omiljeni() {}

    public OmiljeniPK getId() {
        return id;
    }

    public void setId(OmiljeniPK id) {
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
}