/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Miletic
 */
@Entity
public class Korisnik {
    
    @Id()
    @Basic(optional = false)
    @Column(name = "IdK", unique = true)
    private int idK;
    
    @Column(name = "Ime", nullable = false, length = 40)
    private String ime;
    
    @Column(name = "Prezime", nullable = false, length = 40)
    private String prezime;
    
    @Column(name = "Godiste",nullable = false)
    private int godiste;
    
    @Column(name = "Email",nullable = false, length = 40, unique = true)
    private String email;
    
    @Column(name = "Pol", nullable = false, length = 20)
    private String pol;
    
    @Column(name = "Mesto", nullable = false)
    private int mestoId;

    public int getIdK() {
        return idK;
    }

    public void setIdK(int idK) {
        this.idK = idK;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public int getMestoId() {
        return mestoId;
    }

    public void setMestoId(int mestoId) {
        this.mestoId = mestoId;
    }
    
          
    
    
    
}
