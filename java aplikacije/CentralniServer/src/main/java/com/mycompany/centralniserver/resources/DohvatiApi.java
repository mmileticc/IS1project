package com.mycompany.centralniserver.resources;

import Enums.TipPoruke;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import jms.JMSSender;
import org.json.JSONObject;

@Path("dohvati")
public class DohvatiApi {

    

    @Resource(lookup = "Podsistem1Queue")
    private Queue podsistem1Queue;

    @Resource(lookup = "Podsistem2Queue")
    private Queue podsistem2Queue;

    @Resource(lookup = "Podsistem3Queue")
    private Queue podsistem3Queue;
    

    @Inject
    JMSSender sender;
    
    // Zahtev 18 - Dohvatanje svih mesta
    @GET
    @Path("mesta")
    public Response dohvatiSvaMesta() {
        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_SVA_MESTA);
        return sender.posaljiPoruku(json, podsistem1Queue);
    }

    // Zahtev 17 - Dohvatanje svih korisnika
    @GET
    @Path("korisnike")
    public Response dohvatiSveKorisnike() {
        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_SVE_KORISNIKE);
        return sender.posaljiPoruku(json, podsistem1Queue);
    }

    // Zahtev 20 - Dohvatanje svih kategorija
    @GET
    @Path("kategorije")
    public Response dohvatiSveKategorije() {
        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_SVE_KATEGORIJE);
        return sender.posaljiPoruku(json, podsistem2Queue);
    }

    // Zahtev 21 - Dohvatanje svih audio snimaka
    @GET
    @Path("audio/snimke")
    public Response dohvatiSveAudioSnimke() {
        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_SVE_AUDIO_SNIMKE);
        return sender.posaljiPoruku(json, podsistem2Queue);
    }

    // Zahtev 22 - Dohvatanje kategorija audio snimka
    @GET
    @Path("kategorije/{idAudia}")
    public Response dohvatiKategorijeAudioSNimka(@PathParam("idAudia") int idAudia) {
        JSONObject data = new JSONObject();
        data.put("audioId", idAudia);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_KATEGORIJE_AUDIA);
        json.put("data", data);

        return sender.posaljiPoruku(json, podsistem2Queue);
    }

    // Zahtev 23 - Dohvatanje svih paketa
    @GET
    @Path("pakete")
    public Response dohvatiSvePakete() {
        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_SVE_PAKETE);
        return sender.posaljiPoruku(json, podsistem3Queue);
    }

    // Zahtev 24 - Dohvatanje svih pretplata korisnika
    @GET
    @Path("pretplate/{idK}")
    public Response dohvatiSvePretplateKorisnika(@PathParam("idK") int idK) {
        JSONObject data = new JSONObject();
        data.put("korisnikId", idK);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_PRETPLATE_KORISNIKA);
        json.put("data", data);

        return sender.posaljiPoruku(json, podsistem3Queue);
    }

    // Zahtev 25 - Dohvatanje svih slušanja za audio snimak
    @GET
    @Path("slusanja/{idA}")
    public Response dohvatiSvaSlusanjaAudia(@PathParam("idA") int idA) {
        JSONObject data = new JSONObject();
        data.put("audioId", idA);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_SLUSANJA_AUDIA);
        json.put("data", data);

        return sender.posaljiPoruku(json, podsistem3Queue);
    }

    // Zahtev 26 - Dohvatanje svih ocena za audio snimak
    @GET
    @Path("ocene/{idA}")
    public Response dohvatiSveOceneAudia(@PathParam("idA") int idA) {
        JSONObject data = new JSONObject();
        data.put("audioId", idA);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_OCENE_AUDIA);
        json.put("data", data);

        return sender.posaljiPoruku(json, podsistem3Queue);
    }

    // Zahtev 27 - Dohvatanje liste omiljenih audio snimaka korisnika
    @GET
    @Path("omiljene/{idK}")
    public Response dohvatiListuOmiljenihAudiaKorisnika(@PathParam("idK") int idK) {
        JSONObject data = new JSONObject();
        data.put("korisnikId", idK);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.DOHVATI_OMILJENE_AUDIJE);
        json.put("data", data);

        return sender.posaljiPoruku(json, podsistem3Queue);
    }
}