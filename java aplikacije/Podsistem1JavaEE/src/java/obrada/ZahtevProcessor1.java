package obrada;

import Enums.TipPoruke;
import java.io.StringReader;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import servis.KorisnikServis;
import servis.MestoServis;

@ApplicationScoped
public class ZahtevProcessor1 {

    @Inject private KorisnikServis korisnikServis;
    @Inject private MestoServis mestoServis;
    
  
    public String obradi(String json) {
        
        System.out.println("Poruka stigla do obrade u podsistemu 1.)");
        
        JsonObject obj = Json.createReader(new StringReader(json)).readObject();
        String tip = obj.getString("tip");
        JsonObject data = obj.containsKey("data") ? obj.getJsonObject("data") : null;
        TipPoruke t = TipPoruke.valueOf(tip);
        
        switch (t) {
        case KREIRAJ_MESTO:                                 //Zahtev 1
            mestoServis.kreiraj(data);
            return buildStatus("OK", "Mesto kreirano.");

        case KREIRAJ_KORISNIKA:                             //Zahtev 2
            korisnikServis.kreiraj(data);
            return buildStatus("OK", "Korisnik kreiran.");

        case PROMENI_EMAIL:                                 //Zahtev 3
            korisnikServis.promeniEmail(data);
            return buildStatus("OK", "Email ažuriran.");

        case PROMENI_MESTO:                                 //Zahtev 4
            korisnikServis.promeniMesto(data);
            return buildStatus("OK", "Mesto ažurirano.");
       
        case DOHVATI_SVA_MESTA:                             //Zahtev 18
            JsonArray mesta = mestoServis.getSvaMestaAsJson();
            return buildData("OK", mesta);

        case DOHVATI_SVE_KORISNIKE:                         //Zahtev 19
            JsonArray korisnici = korisnikServis.getSviKorisniciAsJson();
            return buildData("OK", korisnici);
        default:
            return buildStatus("GRESKA", "Nepoznat tip zahteva: " + tip);
        
        }
    }

    private String buildStatus(String status, String poruka) {
        JsonObject odgovor = Json.createObjectBuilder()
            .add("status", status)
            .add("poruka", poruka)
            .build();
        return odgovor.toString();
    }
    
    private String buildData(String status, JsonArray data) {
        JsonObject odgovor = Json.createObjectBuilder()
            .add("status", status)
            .add("data", data)
            .build();
        return odgovor.toString();
    }
    
    

    
}