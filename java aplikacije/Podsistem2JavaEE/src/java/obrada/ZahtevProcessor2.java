package obrada;

import Enums.TipPoruke;
import Servis.AudioServis;
import Servis.KategorijaServis;
import java.io.StringReader;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;


@ApplicationScoped
public class ZahtevProcessor2 {

    @Inject private KategorijaServis kategorijaServis;
    @Inject private AudioServis audioServis;
    

    public String obradi(String json) {
        
        System.out.println("Poruka stigla do obrade u podsistemu 2.)");
        
        JsonObject obj = Json.createReader(new StringReader(json)).readObject();
        String tip = obj.getString("tip");
        JsonObject data = obj.containsKey("data") ? obj.getJsonObject("data") : null;
        TipPoruke t = TipPoruke.valueOf(tip);
        
        switch(t){
            case KREIRAJ_KATEGORIJU:                                //Zahtev 5
                System.out.println("ide u kreiranje konacno");
                kategorijaServis.kreiraj(data);
                return buildStatus("OK", "Kategorija kreirana.");
            
            case KREIRAJ_AUDIO_SNIMAK:                              //Zahtev 6
                audioServis.kreiraj(data);
                return buildStatus("OK", "Audio snimak kreiran.");

            case PROMENI_IME_SNIMKA:                                //Zahtev 7
                audioServis.promeniIme(data);
                return buildStatus("OK", "Naziv audio snimka promenjen.");

            case DODAJ_KATEGORIJU_SNIMKU:                           //Zahtev 8
                audioServis.dodajKategoriju(data);
                return buildStatus("OK", "Kategorija dodata snimku.");

            case OBRISI_AUDIO_SNIMAK:                               //Zahtev 17
                audioServis.obrisi(data);
                return buildStatus("OK", "Audio snimak obrisan.");

            case DOHVATI_SVE_KATEGORIJE:                            //Zahtev 20
                JsonArray kategorije = kategorijaServis.getSveKategorijeAsJson();
                return buildData("OK", kategorije);

            case DOHVATI_SVE_AUDIO_SNIMKE:                          //Zahtev 21
                JsonArray snimci = audioServis.getSviSnimciAsJson();
                return buildData("OK", snimci);

            case DOHVATI_KATEGORIJE_AUDIA:                          //Zahtev 22
                JsonArray zaAudio = kategorijaServis.getKategorijeZaAudio(data);
                return buildData("OK", zaAudio);
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