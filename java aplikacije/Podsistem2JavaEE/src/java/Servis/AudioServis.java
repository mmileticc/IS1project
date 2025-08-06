package Servis;

import Entiteti.AudioSnimak;
import Entiteti.Kategorija;
import Entiteti.AudioSnimakKategorija;
import Entiteti.AudioSnimakKategorijaPK;
import Entiteti.Korisnik;
import Enums.TipPoruke;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import javax.json.*;
import javax.persistence.*;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;

import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class AudioServis {
    
    @Inject
    private JMSContext context;

    @Resource(lookup = "SinhronizacijaAudioSnimaka")
    private Queue queueZaSinhronizaciju;

    @PersistenceContext(unitName = "Podsistem2PU")
    private EntityManager em;

    public void kreiraj(JsonObject data) {
        String naziv = data.getString("naziv");
        int trajanje = data.getInt("trajanje");
        String datumStr = data.getString("datum");
        
        int idV = data.getInt("idV");

        Date datum;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            datum = format.parse(datumStr);
        } catch (ParseException e) {
            return;
        }
        Korisnik k = em.find(Korisnik.class, idV);
        if(k == null) {
            System.out.println("Nepostojeci korisnik za vlasnika, neuspeno pravljenje snimka");
            return;
        }
        
        AudioSnimak snimak = new AudioSnimak();
        snimak.setNaziv(naziv);
        snimak.setTrajanje(trajanje);
        snimak.setDatumPostavljanja(datum);
        
        snimak.setVlasnik(k);

        em.persist(snimak);
        em.flush();
        System.out.println("Kreiran audio snimak: " + naziv);

        
        int id = snimak.getIdSnimka();
        
        JsonObject updated = Json.createObjectBuilder(data)
        .add("id", id)
        .build();
        
        //prosledjivanje podsistemu 3 za azuriranje snimaka
        JsonObject payload = Json.createObjectBuilder()
        .add("tip", TipPoruke.KREIRAJ_AUDIO_SNIMAK.toString())
        .add("data", updated)
        .build();

        context.createProducer().send(queueZaSinhronizaciju, payload.toString());
        
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
        
        //prosledjivanje podsistemu 3 za azuriranje snimaka
        JsonObject payload = Json.createObjectBuilder()
        .add("tip", TipPoruke.PROMENI_IME_SNIMKA.toString())
        .add("data", data)
        .build();

        context.createProducer().send(queueZaSinhronizaciju, payload.toString());
    }

    public void dodajKategoriju(JsonObject data) {
        int audioId = data.getInt("audioId");
        int kategorijaId = data.getInt("kategorijaId");

        AudioSnimak snimak = em.find(AudioSnimak.class, audioId);
        Kategorija kategorija = em.find(Kategorija.class, kategorijaId);

        if (snimak == null || kategorija == null) {
            System.out.println(" Snimak ili kategorija nisu pronađeni (audioId: " + audioId + ", kategorijaId: " + kategorijaId + ")");
            return;
        }
        
        AudioSnimakKategorijaPK pk = new AudioSnimakKategorijaPK(audioId, kategorijaId);
        AudioSnimakKategorija veza = new AudioSnimakKategorija();
        
        veza.setId(pk);
        veza.setAudioSnimak(snimak);
        veza.setKategorija(kategorija);

        em.persist(veza);
        System.out.println(" Dodata kategorija [" + kategorija.getNaziv() + "] snimku [" + snimak.getNaziv() + "]");
        
        /*JsonObject payload = Json.createObjectBuilder()
        .add("tip", TipPoruke.DODAJ_KATEGORIJU_SNIMKU.toString())
        .add("data", data)
        .build();

        context.createProducer().send(queueZaSinhronizaciju, payload.toString());*/
    }

    public void obrisi(JsonObject data) {
        int audioId = data.getInt("audioId");
        int korisnikId = data.getInt("korisnikId");

        AudioSnimak snimak = em.find(AudioSnimak.class, audioId);
        if (snimak != null) {
            // Ovde dodaj proveru da li snimak pripada korisniku
            if (snimak.getVlasnik().getIdK()== korisnikId) {
                em.remove(snimak);
                System.out.println(" Snimak [" + audioId + "] obrisan od strane korisnika [" + korisnikId + "] u podsistemu 2");
            } else {
                System.out.println(" Korisnik [" + korisnikId + "] nije vlasnik snimka [" + audioId + "]");
            }
        } else {
            System.out.println(" Ne postoji snimak sa ID: " + audioId);
        }
        
        
        //prosledjivanje podsistemu 3 za azuriranje snimaka
        JsonObject payload = Json.createObjectBuilder()
        .add("tip", TipPoruke.OBRISI_AUDIO_SNIMAK.toString())
        .add("data", data)
        .build();

        context.createProducer().send(queueZaSinhronizaciju, payload.toString());
    }

    public JsonArray getSviSnimciAsJson() {
        List<AudioSnimak> snimci = em.createQuery("SELECT a FROM AudioSnimak a", AudioSnimak.class).getResultList();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (AudioSnimak a : snimci) {
            builder.add(Json.createObjectBuilder()
                .add("id", a.getIdSnimka())
                .add("naziv", a.getNaziv())
                .add("trajanje", a.getTrajanje())
                .add("datum_postavljanja", a.getDatumPostavljanja().toString())
                .add("idV", a.getVlasnik().getIdK())
            );
        }

        System.out.println(" Vraćeni svi snimci kao JSON");
        return builder.build();
    }
}