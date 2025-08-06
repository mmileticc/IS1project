package Servis;

import javax.json.JsonObject;
import javax.persistence.*;
import Entiteti.Korisnik;
import javax.ejb.Stateless;

@Stateless
public class KorisnikServis {

    @PersistenceContext(unitName = "Podsistem3PU")
    private EntityManager em;
    

    public void kreiraj(JsonObject data) {
        System.out.println("stiglo do kreiraj u podsistemu 2");
        
        Korisnik k = new Korisnik();
        k.setIdK(data.getInt("id"));
        k.setIme(data.getString("ime"));
        k.setPrezime(data.getString("prezime"));
        k.setEmail(data.getString("email"));
        k.setGodiste(data.getInt("godiste"));
        k.setPol(data.getString("pol"));        
        k.setMestoId(data.getInt("mestoId"));
        System.out.println("Jos samo persist da se uradi za kornsika u pod2");

        em.persist(k);
    }

    public void promeniEmail(JsonObject data) {
        Korisnik k = em.find(Korisnik.class, data.getInt("id"));
        
        if (k != null) {
            if (data.containsKey("noviEmail")) {
                k.setEmail(data.getString("noviEmail"));
            } else {
                System.out.println("JSON ne sadrži ključ noviEmail!");
                return;
            }
        }else{
            System.out.println("Ne postoji korisnik sa id-jem " + data.getInt("id")+ " u metodi promeni email");
        }
        
    }

    public void promeniMesto(JsonObject data) {
        Korisnik k = em.find(Korisnik.class, data.getInt("id"));
        if (k != null) {
            int mestoId = data.getInt("novoMestoId");
            k.setMestoId(mestoId);
        } 
    }   
   
}