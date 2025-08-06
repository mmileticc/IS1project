package Servis;

import Entiteti.Korisnik;
import Entiteti.Paket;
import Entiteti.Pretplata;
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

@Stateless
public class PretplataServis {

    @PersistenceContext(unitName = "Podsistem3PU")
    private EntityManager em;

    public void kreiraj(JsonObject data) {
        int korisnikId = data.getInt("korisnikId");
        int paketId = data.getInt("paketId");

        Korisnik korisnik = em.find(Korisnik.class, korisnikId);
        Paket paket = em.find(Paket.class, paketId);

        if (korisnik == null || paket == null) {
            System.out.println("❌ Korisnik ili paket ne postoji.");
            return;
        }

        // Provera postojeće pretplate
        List<Pretplata> pretplate = em.createQuery(
            "SELECT p FROM Pretplata p WHERE p.korisnik.idK = :kid ORDER BY p.datumPocetka DESC", Pretplata.class)
            .setParameter("kid", korisnikId)
            .getResultList();

        if (!pretplate.isEmpty()) {
            Pretplata poslednja = pretplate.get(0);
            long dana = (new Date().getTime() - poslednja.getDatumPocetka().getTime()) / (1000 * 60 * 60 * 24);
            if (dana < 30) {
                System.out.println(" Korisnik već ima aktivnu pretplatu (pre manje od 30 dana).");
                return;
            }
        }

        Pretplata nova = new Pretplata();
        nova.setKorisnik(korisnik);
        nova.setPaket(paket);
        nova.setDatumPocetka(new Date());
        nova.setCena(paket.getTrenutnaCena());

        em.persist(nova);
        System.out.println(" Kreirana pretplata za korisnika [" + korisnikId + "] na paket [" + paketId + "]");
    }

    public JsonArray getPretplateZaKorisnika(JsonObject data) {
        int korisnikId = data.getInt("korisnikId");

        List<Pretplata> pretplate = em.createQuery(
            "SELECT p FROM Pretplata p WHERE p.korisnik.idK = :kid", Pretplata.class)
            .setParameter("kid", korisnikId)
            .getResultList();

        JsonArrayBuilder builder = Json.createArrayBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Pretplata p : pretplate) {
            builder.add(Json.createObjectBuilder()
                .add("id", p.getId())
                .add("paketId", p.getPaket().getId())
                .add("nazivPaketa", p.getPaket().getNaziv())
                .add("datumPocetka", sdf.format(p.getDatumPocetka()))
                .add("cena", p.getCena()));
        }

        System.out.println(" Dohvaćene pretplate korisnika [" + korisnikId + "]");
        return builder.build();
    }
}