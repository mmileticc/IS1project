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
@Path("mesto")
public class MestoApi {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "Podsistem1Queue")
    private Queue queue;

    @Inject
    JMSSender sender;
    
    
    //Zahtev 1 - Kreiranje mesta
    @POST
    @Path("kreiraj/{naziv}")
    public Response kreiraj(
            @PathParam("naziv") String naziv
    ) 
    {
        JSONObject data = new JSONObject();
        data.put("naziv", naziv);

        JSONObject json = new JSONObject();
        json.put("tip", TipPoruke.KREIRAJ_MESTO);
        json.put("data", data);
        
        return sender.posaljiPoruku(json, queue);

    }
    
}
