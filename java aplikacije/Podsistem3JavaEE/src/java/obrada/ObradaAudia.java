/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obrada;

import Enums.TipPoruke;
import Servis.AudioServis;
import java.io.StringReader;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Miletic
 */
@Stateless
public class ObradaAudia {
    @Inject 
    private AudioServis audioServis;
      
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
        case KREIRAJ_AUDIO_SNIMAK:                             //Zahtev 2
            audioServis.kreiraj(data);
            return buildStatus("OK", "Korisnik kreiran.");

        case PROMENI_IME_SNIMKA:                                 //Zahtev 3
            audioServis.promeniIme(data);
            return buildStatus("OK", "Email ažuriran.");

        case OBRISI_AUDIO_SNIMAK:                                 //Zahtev 4
            audioServis.obrisi(data);
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
