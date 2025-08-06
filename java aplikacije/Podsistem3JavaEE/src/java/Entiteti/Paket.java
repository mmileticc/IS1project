package Entiteti;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Miletic
 */
@Entity
public class Paket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPaketa")
    private int id;

    @Column(name = "Naziv", nullable = false, length = 50)
    private String naziv;

    @Column(name = "TrenutnaCena", nullable = false)
    private double trenutnaCena;

    // Getteri i setteri

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getTrenutnaCena() {
        return trenutnaCena;
    }

    public void setTrenutnaCena(double trenutnaCena) {
        this.trenutnaCena = trenutnaCena;
    }
}