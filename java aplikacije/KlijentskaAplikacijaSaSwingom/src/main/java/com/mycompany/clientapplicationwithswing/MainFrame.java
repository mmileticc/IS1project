/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientapplicationwithswing;

import com.mycompany.clientapplicationwithswing.panels.PaketiPanel;
import com.mycompany.clientapplicationwithswing.panels.AudioPanel;
import com.mycompany.clientapplicationwithswing.panels.KorisnikPanel;
import com.mycompany.clientapplicationwithswing.panels.MestoPanel;
import com.mycompany.clientapplicationwithswing.panels.OcenaPanel;
import com.mycompany.clientapplicationwithswing.panels.OmiljeniPanel;
import com.mycompany.clientapplicationwithswing.panels.PretplataPanel;
import com.mycompany.clientapplicationwithswing.panels.SlusanjePanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.text.TabExpander;

/**
 *
 * @author Miletic
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Aplikacija");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Mesta", new MestoPanel());
        tabbedPane.addTab("Korisnici", new KorisnikPanel());
        tabbedPane.addTab("Audio snimci", new AudioPanel());
        tabbedPane.addTab("Paketi", new PaketiPanel());
        tabbedPane.addTab("Pretplate", new PretplataPanel());
        tabbedPane.addTab("Ocene", new OcenaPanel());
        tabbedPane.addTab("Omiljeni", new OmiljeniPanel());
        tabbedPane.addTab("Slusanje", new SlusanjePanel());

        add(tabbedPane);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
