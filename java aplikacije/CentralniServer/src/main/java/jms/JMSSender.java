/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author Miletic
 */
public class JMSSender {
   
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "ResponseQueue")
    private Queue responseQueue;
    
    @Inject
    private JMSContext context;   
    
    
    public Response posaljiPoruku(JSONObject json, Queue queue) {
        try /*(JMSContext context = connectionFactory.createContext())*/ {
            TextMessage poruka = context.createTextMessage(json.toString());
            context.createProducer().send(queue, poruka);
            Object tip = json.has("tip") ? json.get("tip") : "Nepoznat tip";
            System.out.println("Poruka poslata na Podsistem  Queue sa tipom poruke " + tip.toString());
            
            
            //
            // Čekaj odgovor sa centralnog Queue-a
            TextMessage odgovor = (TextMessage) context.createConsumer(responseQueue).receive(10000); // timeout 5s
            System.out.println("Stigla poruka sa centralnog servera");
            System.out.println(odgovor.getText());
            
            if (odgovor == null) {
                return Response.status(Response.Status.GATEWAY_TIMEOUT)
                               .entity("Nema odgovora od podsistema u predviđenom vremenu.")
                               .build();
            }

            return Response.ok(odgovor.getText()).build();

            //
            
        } catch (Exception e) {
            return Response.serverError().entity("Greška pri slanju JMS poruke: " + e.getMessage()).build();
        }
    }

}
