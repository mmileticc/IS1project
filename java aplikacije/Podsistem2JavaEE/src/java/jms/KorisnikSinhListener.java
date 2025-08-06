/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import obrada.ObradaKorisnika;

/**
 *
 * @author Miletic
 */

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "SinhronizacijaKorisnika")
    }
)
public class KorisnikSinhListener implements MessageListener{

    @Inject
    private JMSContext context;   
    
    @Inject
    private ObradaKorisnika obradaKorisnika;
    
    @Override
    public void onMessage(Message message) {
        System.out.println("Primljena poruka na Topic za Sinhronizaciju Korisnika");
        try {
            String json = ((TextMessage) message).getText();
            System.out.println("Rezultat sinhronizacije korisnika u podsistemu 2:");
            System.out.println(obradaKorisnika.obradi(json));
        } catch (JMSException e) {
        }
    }
    
}
