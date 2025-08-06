/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servis;

import Entiteti.AudioSnimak;
import Entiteti.Korisnik;
import Entiteti.Omiljeni;
import Entiteti.OmiljeniPK;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Miletic
 */
@Stateless
public class AudioServis {
    
    
    @PersistenceContext(unitName = "Podsistem3PU")
    private EntityManager em;

    public void dodajOmiljeni(JsonObject data) {
        int korisnikId = data.getInt("korisnikId");
        int audioId = data.getInt("audioId");

        OmiljeniPK id = new OmiljeniPK(korisnikId, audioId);
        Omiljeni postojeci = em.find(Omiljeni.class, id);

        if (postojeci != null) {
            System.out.println(" Audio već postoji u omiljenima korisnika [" + korisnikId + "]");
            return;
        }

        Korisnik korisnik = em.find(Korisnik.class, korisnikId);
        AudioSnimak audio = em.find(AudioSnimak.class, audioId);

        if (korisnik == null || audio == null) {
            System.out.println("❌ Greška: korisnik ili audio snimak ne postoje!");
            return;
        }

        Omiljeni novi = new Omiljeni();
        novi.setId(id);
        novi.setKorisnik(korisnik); // ✅ postavi korisnika
        novi.setAudio(audio);       // ✅ postavi audio

        em.persist(novi);
        System.out.println("✅ Dodato u omiljene: audio [" + audioId + "] za korisnika [" + korisnikId + "]");
    }

    public JsonArray getOmiljeniZaKorisnika(JsonObject data) {
        int korisnikId = data.getInt("korisnikId");

        List<Omiljeni> lista = em.createQuery(
            "SELECT o FROM Omiljeni o WHERE o.id.korisnikId = :kid", Omiljeni.class)
            .setParameter("kid", korisnikId)
            .getResultList();

        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Omiljeni o : lista) {
            AudioSnimak a = o.getAudio();
            builder.add(Json.createObjectBuilder()
                .add("audioId", a.getIdSnimka())
                .add("naziv", a.getNaziv())
                .add("trajanje", a.getTrajanje())
                .add("datum_postavljanja", new SimpleDateFormat("yyyy-MM-dd").format(a.getDatumPostavljanja()))
                .add("vlasnikId", a.getVlasnikId()));
        }

        System.out.println(" Vraćeni omiljeni audio snimci za korisnika [" + korisnikId + "]");
        return builder.build();
    }
        
    
    
    
    
    
    public void kreiraj(JsonObject data) {
        String naziv = data.getString("naziv");
        int trajanje = data.getInt("trajanje");
        String datumStr = data.getString("datum");
        int idV = data.getInt("idV");
        int idSnimka = data.getInt("id");
        
        Date datum;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            datum = format.parse(datumStr);
        } catch (ParseException e) {
            return;
        }
       
        
        AudioSnimak snimak = new AudioSnimak();
        snimak.setNaziv(naziv);
        snimak.setTrajanje(trajanje);
        snimak.setDatumPostavljanja(datum);
        snimak.setVlasnikId(idV);
        snimak.setIdSnimka(idSnimka);

        em.persist(snimak);
        System.out.println("Kreiran audio snimak: " + naziv);
    }

    public void obrisi(JsonObject data) {
        int audioId = data.getInt("audioId");
        int korisnikId = data.getInt("korisnikId");

        AudioSnimak snimak = em.find(AudioSnimak.class, audioId);
        if (snimak != null) {
            // Ovde dodaj proveru da li snimak pripada korisniku
            if (snimak.getVlasnikId()== korisnikId) {
                em.remove(snimak);
                System.out.println(" Snimak [" + audioId + "] obrisan od strane korisnika [" + korisnikId + "] i u podsistemu 3");
            } else {
                System.out.println(" Korisnik [" + korisnikId + "] nije vlasnik snimka [" + audioId + "]");
            }
        } else {
            System.out.println(" Ne postoji snimak sa ID: " + audioId);
        }
        
    }

    public void promeniIme(JsonObject data) {
        int id = data.getInt("id");
        String novoIme = data.getString("novoIme");

        AudioSnimak snimak = em.find(AudioSnimak.class, id);
        if (snimak != null) {
            snimak.setNaziv(novoIme);
            em.merge(snimak);
            System.out.println(" Promenjeno ime snimka [" + id + "] u: " + novoIme);
        } else {
            System.out.println(" Nije pronađen snimak sa ID: " + id);
        }
        
    }
    
}
