package Entiteti;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miletic
 */
@Embeddable
public class OcenaPK implements Serializable{

    @Column(name = "KorisnikId")
    private int korisnikId;

    @Column(name = "AudioId")
    private int audioId;

    public OcenaPK() {}

    public OcenaPK(int korisnikId, int audioId) {
        this.korisnikId = korisnikId;
        this.audioId = audioId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OcenaPK)) return false;
        OcenaPK that = (OcenaPK) o;
        return korisnikId == that.korisnikId && audioId == that.audioId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(korisnikId, audioId);
    }
}