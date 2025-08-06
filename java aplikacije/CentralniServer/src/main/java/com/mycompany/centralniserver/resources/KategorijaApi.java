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

@Path("kategorija")
public class KategorijaApi {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "Podsistem2Queue") // pretpostavljeni naziv, proveri u konfiguraciji
    private Queue queue;

    @Inject
    JMSSender sender;
    
    //Zahtev 5 - Kreiranje kategorije
    @POST
    @Path("kreiraj/{naziv}")
    public Response kreirajKategoriju(@PathParam("naziv") String naziv) {
        JSONObject data = new JSONObject();
        data.put("naziv", naziv);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.KREIRAJ_KATEGORIJU);
        json.put("data", data);
        
        return sender.posaljiPoruku(json, queue);
    }
}