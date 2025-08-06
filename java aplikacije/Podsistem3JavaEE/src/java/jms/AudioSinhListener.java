/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import obrada.ObradaAudia;

/**
 *
 * @author Miletic
 */

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "SinhronizacijaAudioSnimaka")
    }
)

public class AudioSinhListener implements MessageListener{

    @Inject
    private ObradaAudia obradaAudia;
    
    @Override
    public void onMessage(Message message) {
        System.out.println("Primljena poruka na Queue za Sinhronizaciju Audio snimaka u podsistemu 3");
        try {
            String json = ((TextMessage) message).getText();
            System.out.println("Rezultat sinhronizacije korisnika u podsistemu 3:");
            System.out.println(obradaAudia.obradi(json));
        } catch (JMSException e) {
        }    }
    
}
