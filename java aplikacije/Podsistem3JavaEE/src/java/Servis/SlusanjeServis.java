package Servis;

import Entiteti.AudioSnimak;
import Entiteti.Korisnik;
import Entiteti.Slusanje;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SlusanjeServis {

    @PersistenceContext(unitName = "Podsistem3PU")
    private EntityManager em;

    public void kreiraj(JsonObject data) {
        int korisnikId = data.getInt("korisnikId");
        int audioId = data.getInt("audioId");
        int pocetniSekund = data.getInt("pocetniSekund");
        int trajanje = data.getInt("trajanje");
        String vremeStr = data.getString("vremePocetka");

        Korisnik korisnik = em.find(Korisnik.class, korisnikId);
        AudioSnimak audio = em.find(AudioSnimak.class, audioId);

        if (korisnik == null || audio == null) {
            System.out.println(" Korisnik ili audio snimak ne postoji.");
            return;
        }

        Date vremePocetka;
        try {
            vremePocetka = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(vremeStr);
        } catch (ParseException e) {
            System.out.println(" Neispravan format vremena: " + vremeStr);
            return;
        }

        Slusanje slusanje = new Slusanje();
        slusanje.setKorisnik(korisnik);
        slusanje.setAudio(audio);
        slusanje.setPocetakVreme(vremePocetka);
        slusanje.setSekundiOd(pocetniSekund);
        slusanje.setSekundiTrajanje(trajanje);

        em.persist(slusanje);
        System.out.println("Zabeleženo slušanje za korisnika [" + korisnikId + "] snimka [" + audioId + "]");
    }

    public JsonArray getSlusanjaZaAudio(JsonObject data) {
        int audioId = data.getInt("audioId");

        List<Slusanje> slusanja = em.createQuery(
            "SELECT s FROM Slusanje s WHERE s.audio.idSnimka = :aid", Slusanje.class)
            .setParameter("aid", audioId)
            .getResultList();

        JsonArrayBuilder builder = Json.createArrayBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Slusanje s : slusanja) {
            builder.add(Json.createObjectBuilder()
                .add("id", s.getId())
                .add("korisnikId", s.getKorisnik().getIdK())
                .add("vremePocetka", sdf.format(s.getPocetakVreme()))
                .add("pocetniSekund", s.getSekundiOd())
                .add("trajanje", s.getSekundiTrajanje()));
        }

        System.out.println("Vraćena slušanja za audio [" + audioId + "]");
        return builder.build();
    }
}