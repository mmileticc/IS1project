package servis;

import javax.json.JsonObject;
import javax.persistence.*;
import Entiteti.Korisnik;
import Entiteti.Mesto;
import Enums.TipPoruke;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

@Stateless
public class KorisnikServis {

    @PersistenceContext(unitName = "Podsistem1PU")
    private EntityManager em;
    

    @Resource(lookup = "SinhronizacijaKorisnika")
    private Topic topicZaSinhronizaciju;

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    private JMSContext context;

    @PostConstruct
    private void init() {
        context = connectionFactory.createContext();
    }
    

    public void kreiraj(JsonObject data) {
        Korisnik k = new Korisnik();
        k.setIme(data.getString("ime"));
        k.setPrezime(data.getString("prezime"));
        k.setEmail(data.getString("email"));
        k.setGodiste(data.getInt("godiste"));
        k.setPol(data.getString("pol"));

        int mestoId = data.getInt("mestoId");
        Mesto m = em.find(Mesto.class, mestoId);
        if(m == null) {
            System.out.println("Nije uspesno dohvaceno mesto na onsovu idMesta koji je " + mestoId + " pri kreiranju korisnika");
            return;
        }
        k.setMesto(m);
        em.persist(k);
        em.flush(); 
        JsonObject updated = Json.createObjectBuilder(data)
        .add("id", k.getIdK())
        .build();
        
        //poslati na topic podsistemima 2 i 3 da naprave korisnika
        JsonObject sinhronaPoruka = Json.createObjectBuilder()
        .add("tip", TipPoruke.KREIRAJ_KORISNIKA.toString())
        .add("data", updated)
        .build();

        context.createProducer().send(topicZaSinhronizaciju, sinhronaPoruka.toString());
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
            //poslati na topic podsistemima 2 i 3 da azuriraju korisniku email
            JsonObject sinhronaPoruka = Json.createObjectBuilder()
            .add("tip", TipPoruke.PROMENI_EMAIL.toString())
            .add("data", data)
            .build();

            context.createProducer().send(topicZaSinhronizaciju, sinhronaPoruka.toString());

        }else{
            System.out.println("Ne postoji korisnik sa id-jem " + data.getInt("id")+ " u metodi promeni email");
        }
        
    }

    public void promeniMesto(JsonObject data) {
        Korisnik k = em.find(Korisnik.class, data.getInt("id"));
        if (k != null) {
            int mestoId = data.getInt("novoMestoId");
            Mesto novoMesto = em.find(Mesto.class, mestoId);
            if (novoMesto != null) {
                k.setMesto(novoMesto);

                //poslati na topic podsistemima 2 i 3 da azurirjaju korisniku mesto
                JsonObject payload = Json.createObjectBuilder()
                .add("tip", TipPoruke.PROMENI_MESTO.toString())
                .add("data", data)
                .build();

                context.createProducer().send(topicZaSinhronizaciju, payload.toString());
            } else {
                System.out.println("Mesto sa ID " + mestoId + " ne postoji.");
            }
        } else {
            System.out.println("Korisnik sa ID " + data.getInt("id") + " ne postoji.");
        }
    }
    public List<Korisnik> dohvatiSveKorisnike() {
        return em.createQuery("SELECT k FROM Korisnik k", Korisnik.class).getResultList();
    }
    
    public JsonArray getSviKorisniciAsJson() {
        List<Korisnik> korisnici = dohvatiSveKorisnike();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for(Korisnik k : korisnici) {
            builder.add(Json.createObjectBuilder()
                .add("id", k.getIdK())
                .add("ime", k.getIme())
                .add("prezime", k.getPrezime())
                .add("email", k.getEmail())
                .add("godiste", k.getGodiste())
                .add("pol", k.getPol())
                .add("mestoId", k.getMesto().getIdM())
                .add("mestoNaziv", k.getMesto().getNaziv()));
        }

        return builder.build();
    }
}