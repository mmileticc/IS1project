package com.mycompany.centralniserver.resources;

import Enums.TipPoruke;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Queue;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import jms.JMSSender;
import org.json.JSONObject;

@Path("audio")
public class AudioSnimakApi {

    
    @Resource(lookup = "Podsistem2Queue")
    private Queue queue;
    
    @Inject
    JMSSender sender;



    //Zahtev 6 - Kreiranje audio snika
    @POST
    @Path("kreiraj/{datumPostavljanja}/{naziv}/{trajanje}/{idVlasnika}")
    public Response kreirajAudioSnimak(
            @PathParam("datumPostavljanja") String datumStr,
            @PathParam("naziv") String naziv,
            @PathParam("trajanje") int trajanje,
            @PathParam("idVlasnika") int idV
    ) {
        Date datum;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            datum = format.parse(datumStr);
        } catch (ParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Nevalidan format datuma. Očekuje se ISO format: yyyy-MM-dd")
                           .build();
        }

        JSONObject data = new JSONObject();
        data.put("naziv", naziv);
        data.put("trajanje", trajanje);
        data.put("datum", datumStr); // Možeš i format.format(datum) ako želiš da normalizuješ
        data.put("idV", idV);
 
        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.KREIRAJ_AUDIO_SNIMAK);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }


    // Zahtev 7 - Promena imena audio snimku
    @PUT
    @Path("promeni/ime/{id}/{novoIme}")
    public Response promeniImeAudioSnimku(
            @PathParam("id") int id,
            @PathParam("novoIme") String novoIme
    ) {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("novoIme", novoIme);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.PROMENI_IME_SNIMKA);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }

    // Zahtev 8 - DodavanjeKategorije audio snimku
    @PUT
    @Path("dodaj/kategoriju/{audioId}/{kategorijaId}")
    public Response dodajKategoriju(
            @PathParam("audioId") int audioId,
            @PathParam("kategorijaId") int kategorijaId
    ) {
        JSONObject data = new JSONObject();
        data.put("audioId", audioId);
        data.put("kategorijaId", kategorijaId);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DODAJ_KATEGORIJU_SNIMKU);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }
    
    
    // Zahtev 17 - Brisanje audio snimka od strane vlasnika
    @DELETE
    @Path("obrisi/{audioId}/{korisnikId}")
    public Response obrisiAudioSnimak(
            @PathParam("audioId") int audioId,
            @PathParam("korisnikId") int korisnikId
    ) {
        JSONObject data = new JSONObject();
        data.put("audioId", audioId);
        data.put("korisnikId", korisnikId);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.OBRISI_AUDIO_SNIMAK);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }
}