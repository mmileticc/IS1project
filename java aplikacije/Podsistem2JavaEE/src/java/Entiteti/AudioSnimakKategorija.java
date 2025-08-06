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
public class AudioSnimakKategorija  {

    @EmbeddedId
    private AudioSnimakKategorijaPK id;

    @JoinColumn(name = "AudioId", referencedColumnName = "IdSnimka", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AudioSnimak audioSnimak;

    @JoinColumn(name = "KategorijaId", referencedColumnName = "IdKategorije", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kategorija kategorija;

    public AudioSnimakKategorija() {}

    public AudioSnimakKategorija(AudioSnimakKategorijaPK id) {
        this.id = id;
    }

    public AudioSnimakKategorijaPK getId() {
        return id;
    }

    public void setId(AudioSnimakKategorijaPK id) {
        this.id = id;
    }

    public AudioSnimak getAudioSnimak() {
        return audioSnimak;
    }

    public void setAudioSnimak(AudioSnimak audioSnimak) {
        this.audioSnimak = audioSnimak;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }
}