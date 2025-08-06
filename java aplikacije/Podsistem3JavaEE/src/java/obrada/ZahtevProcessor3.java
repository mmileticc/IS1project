package obrada;

import Enums.TipPoruke;
import Servis.AudioServis;
import Servis.OcenaServis;
import Servis.PaketServis;
import Servis.PretplataServis;
import Servis.SlusanjeServis;
import java.io.StringReader;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.json.*;


@ApplicationScoped
public class ZahtevProcessor3 {

    @Inject private AudioServis audioServis;
    @Inject private OcenaServis ocenaServis;
    @Inject private PaketServis paketServis;
    @Inject private PretplataServis pretplataServis;
    @Inject private SlusanjeServis slusanjeServis;
          
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

   

    public String obradi(String json) {
        
        System.out.println("Poruka stigla do obrade.)");
        
        JsonObject obj = Json.createReader(new StringReader(json)).readObject();
        String tip = obj.getString("tip");
        JsonObject data = obj.containsKey("data") ? obj.getJsonObject("data") : null;
        TipPoruke t = TipPoruke.valueOf(tip);
        
        switch(t){
                case KREIRAJ_PAKET:                                     //Zahtev 9
                paketServis.kreiraj(data);
                return buildStatus("OK", "Paket kreiran.");

            case PROMENI_CENU_PAKETA:                                   //Zahtev 10
                paketServis.promeniCenu(data);
                return buildStatus("OK", "Cena paketa ažurirana.");

            case KREIRAJ_PRETPLATU:                                     //Zahtev 11
                pretplataServis.kreiraj(data);
                return buildStatus("OK", "Pretplata uspešno registrovana.");

            case KREIRAJ_SLUSANJE:                                      //Zahtev 12
                slusanjeServis.kreiraj(data);
                return buildStatus("OK", "Slušanje zabeleženo.");

            case DODAJ_OMILJENI:                                        //Zahtev 13
                audioServis.dodajOmiljeni(data);
                return buildStatus("OK", "Audio dodat u omiljene.");

            case KREIRAJ_OCENU:                                         //Zahtev 14
                ocenaServis.kreiraj(data);
                return buildStatus("OK", "Ocena dodata.");

            case IZMENA_OCENE:                                          //Zahtev 15
                ocenaServis.izmeni(data);
                return buildStatus("OK", "Ocena izmenjena.");

            case OBRISI_OCENU:
                ocenaServis.obrisi(data);                               //Zahtev 16
                return buildStatus("OK", "Ocena obrisana.");

            case DOHVATI_SVE_PAKETE:                                    //Zahtev 23
                JsonArray paketi = paketServis.getSviPaketiAsJson();
                return buildData("OK", paketi);

            case DOHVATI_PRETPLATE_KORISNIKA:                           //Zahtev 24
                JsonArray pretplate = pretplataServis.getPretplateZaKorisnika(data);
                return buildData("OK", pretplate);

            case DOHVATI_SLUSANJA_AUDIA:                                //Zahtev 25
                JsonArray slusanja = slusanjeServis.getSlusanjaZaAudio(data);
                return buildData("OK", slusanja);

            case DOHVATI_OCENE_AUDIA:                                   //Zahtev 26
                JsonArray ocene = ocenaServis.getOceneZaAudio(data);
                return buildData("OK", ocene);

            case DOHVATI_OMILJENE_AUDIJE:                               //Zahtev 27
                JsonArray omiljeni = audioServis.getOmiljeniZaKorisnika(data);
                return buildData("OK", omiljeni);
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