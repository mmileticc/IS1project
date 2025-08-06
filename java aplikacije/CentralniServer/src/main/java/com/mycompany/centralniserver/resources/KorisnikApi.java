package com.mycompany.centralniserver.resources;

import Enums.TipPoruke;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import jms.JMSSender;
import org.json.JSONObject;

@Path("korisnik")
public class KorisnikApi {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "Podsistem1Queue")
    private Queue queue;

    @Inject
    JMSSender sender;
    

    //Zahtev 2 - Kreiranje korisnika
    @POST
    @Path("kreiraj/{ime}/{prezime}/{email}/{godiste}/{pol}/{mestoId}")
    public Response kreirajKorisnika(
            @PathParam("ime") String ime,
            @PathParam("prezime") String prezime,
            @PathParam("email") String email,
            @PathParam("godiste") int godiste,
            @PathParam("pol") String pol,
            @PathParam("mestoId") int mestoId
    ) {
        JSONObject data = new JSONObject();
        data.put("ime", ime);
        data.put("prezime", prezime);
        data.put("email", email);
        data.put("godiste", godiste);
        data.put("pol", pol);
        data.put("mestoId", mestoId);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.KREIRAJ_KORISNIKA);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }

    //Zahtev 3 - Promena emaila korisniku
    @PUT
    @Path("promeni/email/{id}/{noviEmail}")
    public Response promeniEmailKorisniku(
            @PathParam("id") int id,
            @PathParam("noviEmail") String noviEmail
    ) {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("noviEmail", noviEmail);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.PROMENI_EMAIL);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }

    //Zahtev 4 - Promena mesta korisniku
    @PUT
    @Path("promeni/mesto/{id}/{novoMestoId}")
    public Response promeniMestoKorisniku(
            @PathParam("id") int id,
            @PathParam("novoMestoId") int novoMestoId
    ) {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("novoMestoId", novoMestoId);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.PROMENI_MESTO);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue);
    }
}