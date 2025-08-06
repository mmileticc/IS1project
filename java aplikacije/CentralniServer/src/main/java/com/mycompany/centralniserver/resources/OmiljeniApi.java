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

@Path("omiljeni")
public class OmiljeniApi {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "Podsistem3Queue")
    private Queue queue;
    
    @Inject
    JMSSender sender;


    // Zahtev 13 - Dodavanje audio snimka u listu omiljenih korisnika
    @POST
    @Path("dodaj/{korisnikId}/{audioId}")
    public Response dodajOmiljeniAudio(
            @PathParam("korisnikId") int korisnikId,
            @PathParam("audioId") int audioId
    ) {
        JSONObject data = new JSONObject();
        data.put("korisnikId", korisnikId);
        data.put("audioId", audioId);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DODAJ_OMILJENI);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }
}