/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserver.resources;

import Enums.TipPoruke;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import jms.JMSSender;
import org.json.JSONObject;

/**
 *
 * @author Miletic
 */
@Path("slusanje")
public class SlusanjeApi {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "Podsistem3Queue")
    private Queue queue;
    
    @Inject
    JMSSender sender;
    
    // Zahtev 12 - Kreiranje slusanja audio snimka od strane korisnika
    @POST
    @Path("kreiraj/{korisnikId}/{audioId}/{vremePocetka}/{pocetniSekund}/{trajanje}")
    public Response kreirajSlusanje(
        @PathParam("korisnikId") int korisnikId,
        @PathParam("audioId") int audioId,
        @PathParam("vremePocetka") String vremePocetka, // može kao ISO string
        @PathParam("pocetniSekund") int pocetniSekund,
        @PathParam("trajanje") int trajanje
    ) {
        JSONObject data = new JSONObject();
        data.put("korisnikId", korisnikId);
        data.put("audioId", audioId);
        data.put("vremePocetka", vremePocetka);
        data.put("pocetniSekund", pocetniSekund);
        data.put("trajanje", trajanje);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.KREIRAJ_SLUSANJE);
        json.put("data", data);

        return sender.posaljiPoruku(json, queue); 
    }
}
