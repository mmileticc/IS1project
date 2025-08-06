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

@Path("paket")
public class PaketApi {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "Podsistem3Queue")
    private Queue queue;

    @Inject
    JMSSender sender;
    
    

    // Zahtev 9 - Kreiranje paketa
    @POST
    @Path("kreiraj/{naziv}/{cena}")
    public Response kreirajPaket(
            @PathParam("naziv") String naziv,
            @PathParam("cena") double cena
    ) {
        JSONObject data = new JSONObject();
        data.put("naziv", naziv);
        data.put("cena", cena);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.KREIRAJ_PAKET);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }

    // Zahtev 10 - Promena cene paketa
    @PUT
    @Path("promeni/cenu/{id}/{novaCena}")
    public Response promeniMesecnuCenu(
            @PathParam("id") int id,
            @PathParam("novaCena") double novaCena
    ) {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("novaCena", novaCena);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.PROMENI_CENU_PAKETA);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }

    
    // Zahtev 11 - Kreiranje pretplate korisnika na paket
    @POST 
    @Path("kreiraj/pretplatu/{korisnikId}/{paketId}")
    public Response kreirajPretplatuKorisnika(
            @PathParam("korisnikId") int korisnikId,
            @PathParam("paketId") int paketId
    ) {
        JSONObject data = new JSONObject();
        data.put("korisnikId", korisnikId);
        data.put("paketId", paketId);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.KREIRAJ_PRETPLATU);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }
}