package Servis;

import Entiteti.Paket;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PaketServis {

    @PersistenceContext
    EntityManager em;

    public void kreiraj(JsonObject data) {
        String naziv = data.getString("naziv");
        double cena = data.getJsonNumber("cena").doubleValue();

        Paket p = new Paket();
        p.setNaziv(naziv);
        p.setTrenutnaCena(cena);

        em.persist(p);
    }

    public void promeniCenu(JsonObject data) {
        int id = data.getInt("id");
        double novaCena = data.getJsonNumber("novaCena").doubleValue();

        Paket p = em.find(Paket.class, id);
        if (p != null) {
            p.setTrenutnaCena(novaCena);
        }
    }

    public JsonArray getSviPaketiAsJson() {
        List<Paket> paketi = em.createQuery("SELECT p FROM Paket p", Paket.class).getResultList();

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Paket p : paketi) {
            JsonObject obj = Json.createObjectBuilder()
                .add("id", p.getId())
                .add("naziv", p.getNaziv())
                .add("cena", p.getTrenutnaCena())
                .build();
            arrayBuilder.add(obj);
        }

        return arrayBuilder.build();
    }
}