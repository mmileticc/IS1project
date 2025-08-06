/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obrada;

import Enums.TipPoruke;
import java.io.StringReader;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import Servis.KorisnikServis;

/**
 *
 * @author Miletic
*/
@ApplicationScoped
public class ObradaKorisnika {
    
    @Inject 
    private KorisnikServis korisnikServis;
      
    public String obradi(String json) {
        
        System.out.println("Poruka stigla do obrade korisnika u podsistemu 3, ovo je stiglo:");
        System.out.println(json);
        JsonObject obj = Json.createReader(new StringReader(json)).readObject();
        String tip = obj.getString("tip");
        JsonObject data = obj.containsKey("data") ? obj.getJsonObject("data") : null;
        
        if(data == null) {
            System.out.println("Data je null");
            return "Data is sent null nekako";
        }
        
        TipPoruke t = TipPoruke.valueOf(tip);
        
        switch (t) {
        case KREIRAJ_KORISNIKA:                             //Zahtev 2
            korisnikServis.kreiraj(data);
            return buildStatus("OK", "Korisnik kreiran.");

        case PROMENI_EMAIL:                                 //Zahtev 3
            korisnikServis.promeniEmail(data);
            return buildStatus("OK", "Email ažuriran.");

        case PROMENI_MESTO:                                 //Zahtev 4
            korisnikServis.promeniMesto(data);
            return buildStatus("OK", "Mesto ažurirano.");
       
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
    
    
}
