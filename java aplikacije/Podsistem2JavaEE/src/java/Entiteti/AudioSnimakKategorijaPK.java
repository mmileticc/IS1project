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
public class AudioSnimakKategorijaPK implements Serializable {

    @Column(name = "AudioId")
    private int audioId;

    @Column(name = "KategorijaId")
    private int kategorijaId;

    public AudioSnimakKategorijaPK() {}

    public AudioSnimakKategorijaPK(int audioId, int kategorijaId) {
        this.audioId = audioId;
        this.kategorijaId = kategorijaId;
    }

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public int getKategorijaId() {
        return kategorijaId;
    }

    public void setKategorijaId(int kategorijaId) {
        this.kategorijaId = kategorijaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AudioSnimakKategorijaPK)) return false;
        AudioSnimakKategorijaPK that = (AudioSnimakKategorijaPK) o;
        return audioId == that.audioId && kategorijaId == that.kategorijaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(audioId, kategorijaId);
    }
}