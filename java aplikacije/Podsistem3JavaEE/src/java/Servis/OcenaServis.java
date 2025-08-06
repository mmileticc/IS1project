package Servis;

import Entiteti.Ocena;
import Entiteti.OcenaPK;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OcenaServis {

    @PersistenceContext
    EntityManager em;

    public void kreiraj(JsonObject data) {
        int korisnikId = data.getInt("korisnikId");
        int audioId = data.getInt("audioId");
        int vrednost = data.getInt("vrednost");
        String vreme = data.getString("vreme");

        OcenaPK id = new OcenaPK(korisnikId, audioId);

        // Provera da li već postoji ocena
        Ocena postojeca = em.find(Ocena.class, id);
        if (postojeca != null) {
            throw new RuntimeException("Ocena već postoji za ovaj par korisnik/audio.");
        }

        Ocena nova = new Ocena();
        nova.setId(id);
        nova.setOcena(vrednost);

        try {
            Date parsed = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(vreme);
            nova.setDatumVreme(parsed);
        } catch (Exception e) {
            throw new RuntimeException("Nevalidan format vremena. Očekuje se ISO: yyyy-MM-dd'T'HH:mm:ss");
        }

        em.persist(nova);
    }

    public void izmeni(JsonObject data) {
        int korisnikId = data.getInt("korisnikId");
        int audioId = data.getInt("audioId");
        int novaOcena = data.getInt("vrednost");

        OcenaPK id = new OcenaPK(korisnikId, audioId);
        Ocena o = em.find(Ocena.class, id);

        if (o != null) {
            o.setOcena(novaOcena);
        }
    }

    public void obrisi(JsonObject data) {
        int korisnikId = data.getInt("korisnikId");
        int audioId = data.getInt("audioId");

        OcenaPK id = new OcenaPK(korisnikId, audioId);
        Ocena o = em.find(Ocena.class, id);

        if (o != null) {
            em.remove(o);
        }
    }

    public JsonArray getOceneZaAudio(JsonObject data) {
        int audioId = data.getInt("audioId");

        List<Ocena> ocene = em.createQuery(
            "SELECT o FROM Ocena o WHERE o.id.audioId = :audioId", Ocena.class)
            .setParameter("audioId", audioId)
            .getResultList();

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Ocena o : ocene) {
            JsonObject obj = Json.createObjectBuilder()
                .add("korisnikId", o.getId().getKorisnikId())
                .add("audioId", o.getId().getAudioId())
                .add("vrednost", o.getOcena())
                .add("vreme", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(o.getDatumVreme()))
                .build();
            arrayBuilder.add(obj);
        }

        return arrayBuilder.build();
    }
}