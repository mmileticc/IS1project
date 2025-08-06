package jms;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import obrada.ZahtevProcessor2;


@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "Podsistem2Queue")
    }
)
public class Podsistem2Listener implements MessageListener {

    @Inject
    private ZahtevProcessor2 zahtevProcessor;

    @Resource(lookup = "ResponseQueue")
    private Queue responseQueue;
    
    @Inject
    private JMSContext context;   
    
    @Override
    public void onMessage(Message message) {
        System.out.println("Primljena poruka na Queue Podistem2");
        try {
            String json = ((TextMessage) message).getText();
            String response = zahtevProcessor.obradi(json);
            //System.out.println(response);   //ispisao provere radi
            
            TextMessage poruka = context.createTextMessage(response);
            context.createProducer().send(responseQueue, poruka);
            System.out.println("Poslata poruka na ResJMSExceptione");
        } catch (JMSException e) {
        }
    }

}