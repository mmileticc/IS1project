package com.mycompany.centralniserver.resources;

import Enums.TipPoruke;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import jms.JMSSender;
import org.json.JSONObject;

@Path("ocena")
public class OcenaApi {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "Podsistem3Queue")
    private Queue queue;

    @Inject
    JMSSender sender;
    
    
    // Zahtev 14 - Kreiranje ocene korisnika za audio cnimak
    @POST
    @Path("kreiraj/{korisnikId}/{audioId}/{vrednost}/{vreme}")
    public Response dodajOcenu(
            @PathParam("korisnikId") int korisnikId,
            @PathParam("audioId") int audioId,
            @PathParam("vrednost") int vrednost,
            @PathParam("vreme") String vreme // ISO format preporučen
    ) {
        if (vrednost < 1 || vrednost > 5) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Ocena mora biti između 1 i 5").build();
        }

        JSONObject data = new JSONObject();
        data.put("korisnikId", korisnikId);
        data.put("audioId", audioId);
        data.put("vrednost", vrednost);
        data.put("vreme", vreme);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.KREIRAJ_OCENU);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
       
    }
    
    
    // Zahtev 15 - Menjanje ocene korisnika za audio cnimak
    @PUT
    @Path("izmeni/{korisnikId}/{audioId}/{novaOcena}")
    public Response izmeniOcenu(
            @PathParam("korisnikId") int korisnikId,
            @PathParam("audioId") int audioId,
            @PathParam("novaOcena") int novaOcena
    ) {
        if (novaOcena < 1 || novaOcena > 5) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Ocena mora biti između 1 i 5").build();
        }

        JSONObject data = new JSONObject();
        data.put("korisnikId", korisnikId);
        data.put("audioId", audioId);
        data.put("vrednost", novaOcena);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.IZMENA_OCENE);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }
    
    
    // Zahtev 16 - Brisanje ocene korisnika za audio cnimak
    @DELETE
    @Path("obrisi/{audioId}/{korisnikId}")
    public Response obrisiOcenu(
            @PathParam("audioId") int audioId,
            @PathParam("korisnikId") int korisnikId
    ) {
        JSONObject data = new JSONObject();
        data.put("audioId", audioId);
        data.put("korisnikId", korisnikId);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.OBRISI_OCENU);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }
    
}