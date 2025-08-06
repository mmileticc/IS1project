package Servis;

import Entiteti.Kategorija;
import Entiteti.AudioSnimak;
import Entiteti.AudioSnimakKategorija;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.*;
import javax.persistence.*;

@Stateless
public class KategorijaServis {

    @PersistenceContext(unitName = "Podsistem2PU")
    private EntityManager em;

    public void kreiraj(JsonObject data) {
        String naziv = data.getString("naziv");
        Kategorija kategorija = new Kategorija();
        kategorija.setNaziv(naziv);
        em.persist(kategorija);

        System.out.println(" Kreirana kategorija: " + naziv);
    }

    public JsonArray getSveKategorijeAsJson() {
        List<Kategorija> kategorije = em.createQuery("SELECT k FROM Kategorija k", Kategorija.class).getResultList();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Kategorija k : kategorije) {
            builder.add(Json.createObjectBuilder()
                .add("id", k.getIdKategorije())
                .add("naziv", k.getNaziv()));
        }
        System.out.println(" Vraćene sve kategorije");
        return builder.build();
    }

    public JsonArray getKategorijeZaAudio(JsonObject data) {
        int audioId = data.getInt("audioId");
        AudioSnimak snimak = em.find(AudioSnimak.class, audioId);

        if (snimak == null) {
            System.out.println("⚠️ Snimak sa ID " + audioId + " ne postoji.");
            return Json.createArrayBuilder().build();
        }

        List<AudioSnimakKategorija> veze = em.createQuery(
            "SELECT ak FROM AudioSnimakKategorija ak WHERE ak.id.audioId = :audioId", AudioSnimakKategorija.class)
            .setParameter("audioId", audioId)
            .getResultList();

        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (AudioSnimakKategorija veza : veze) {
            Kategorija kat = veza.getKategorija();
            builder.add(Json.createObjectBuilder()
                .add("id", kat.getIdKategorije())
                .add("naziv", kat.getNaziv()));
        }

        System.out.println("Vraćene kategorije za snimak [" + audioId + "]");
        return builder.build();
    }
}