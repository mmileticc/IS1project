package com.mycompany.clientapplicationwithswing.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class KorisnikPanel extends JPanel {

    private JTextField tfIme, tfPrezime, tfEmail, tfGodiste, tfPol, tfMestoId;
    private JTextField tfIdEmail, tfNoviEmail;
    private JTextField tfIdMesto, tfNovoMesto;

    private JButton btnKreiraj, btnPromeniEmail, btnPromeniMesto, btnOsvezi;
    private JTable tabelaKorisnika;
    private DefaultTableModel tabelaModel;

    private static final String BASE_URL = "http://localhost:8080/CentralniServer";

    public KorisnikPanel() {
        setLayout(new BorderLayout());

        JPanel formaPanel = new JPanel(new GridLayout(3, 1));
        formaPanel.add(initKreirajPanel());
        formaPanel.add(initEmailPanel());
        formaPanel.add(initMestoPanel());

        add(formaPanel, BorderLayout.NORTH);
        add(initTabelaPanel(), BorderLayout.CENTER);

        dohvatiSveKorisnike(); // Učitavanje tabele pri pokretanju
        
        
    }

    private JPanel initKreirajPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Kreiraj korisnika"));

        tfIme = new JTextField();
        tfPrezime = new JTextField();
        tfEmail = new JTextField();
        tfGodiste = new JTextField();
        tfPol = new JTextField();
        tfMestoId = new JTextField();
        btnKreiraj = new JButton("Kreiraj korisnika");

        panel.add(new JLabel("Ime:")); panel.add(tfIme);
        panel.add(new JLabel("Prezime:")); panel.add(tfPrezime);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Godište:")); panel.add(tfGodiste);
        panel.add(new JLabel("Pol:")); panel.add(tfPol);
        panel.add(new JLabel("Mesto ID:")); panel.add(tfMestoId);
        panel.add(new JLabel("")); panel.add(btnKreiraj);

        btnKreiraj.addActionListener(e -> {
            String url = String.format("%s/korisnik/kreiraj/%s/%s/%s/%s/%s/%s", BASE_URL,
                    tfIme.getText(), tfPrezime.getText(), tfEmail.getText(),
                    tfGodiste.getText(), tfPol.getText(), tfMestoId.getText());
            sendRequest("POST", url);
        });

        return panel;
    }

    private JPanel initEmailPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Promeni email"));

        tfIdEmail = new JTextField();
        tfNoviEmail = new JTextField();
        btnPromeniEmail = new JButton("Promeni email");

        panel.add(new JLabel("ID korisnika:")); panel.add(tfIdEmail);
        panel.add(new JLabel("Novi email:")); panel.add(tfNoviEmail);
        panel.add(new JLabel("")); panel.add(btnPromeniEmail);

        btnPromeniEmail.addActionListener(e -> {
            String url = String.format("%s/korisnik/promeni/email/%s/%s", BASE_URL,
                    tfIdEmail.getText(), tfNoviEmail.getText());
            sendRequest("PUT", url);
        });

        return panel;
    }

    private JPanel initMestoPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Promeni mesto"));

        tfIdMesto = new JTextField();
        tfNovoMesto = new JTextField();
        btnPromeniMesto = new JButton("Promeni mesto");

        panel.add(new JLabel("ID korisnika:")); panel.add(tfIdMesto);
        panel.add(new JLabel("Novo mesto ID:")); panel.add(tfNovoMesto);
        panel.add(new JLabel("")); panel.add(btnPromeniMesto);

        btnPromeniMesto.addActionListener(e -> {
            String url = String.format("%s/korisnik/promeni/mesto/%s/%s", BASE_URL,
                    tfIdMesto.getText(), tfNovoMesto.getText());
            sendRequest("PUT", url);
        });

        return panel;
    }

    private JPanel initTabelaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista korisnika"));

        tabelaModel = new DefaultTableModel();
        tabelaModel.setColumnIdentifiers(new String[]{"ID", "Ime", "Prezime", "Email", "Godiste", "Pol", "MestoId"});

        tabelaKorisnika = new JTable(tabelaModel);
        JScrollPane scrollPane = new JScrollPane(tabelaKorisnika);

        btnOsvezi = new JButton("Osveži listu");
        btnOsvezi.addActionListener(e -> dohvatiSveKorisnike());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnOsvezi, BorderLayout.SOUTH);

        return panel;
    }

    private void sendRequest(String method, String urlStr) {
        SwingWorker<Void, Void> worker = new SwingWorkerImpl(urlStr, method);
        worker.execute();
    }

    private void dohvatiSveKorisnike() {
        SwingWorker<Void, Void> worker = new SwingWorkerImpl1();
        worker.execute();
    }

    private void updateTable(String jsonStr) {
    SwingUtilities.invokeLater(() -> {
        try {
            tabelaModel.setRowCount(0);
            JSONObject root = new JSONObject(jsonStr);
            
            if (!root.optString("status").equals("OK")) {
                JOptionPane.showMessageDialog(null, "Greška sa servera: " + root.optString("poruka"));
                return;
            }

            JSONArray korisnici = root.optJSONArray("data"); // ovo je sada tačan ključ

            if (korisnici != null) {
                for (int i = 0; i < korisnici.length(); i++) {
                    JSONObject k = korisnici.getJSONObject(i);
                    tabelaModel.addRow(new Object[]{
                        k.optInt("id"), k.optString("ime"), k.optString("prezime"),
                        k.optString("email"), k.optInt("godiste"),
                        k.optString("pol"), k.optInt("mestoId")
                    });
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Greška pri parsiranju JSON-a: " + ex.getMessage());
        }
    });
}

    private static class SwingWorkerImpl extends SwingWorker<Void, Void> {

        private final String urlStr;
        private final String method;

        public SwingWorkerImpl(String urlStr, String method) {
            this.urlStr = urlStr;
            this.method = method;
        }

        @Override
        protected Void doInBackground() {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                con.setRequestMethod(method);
                con.setRequestProperty("Accept", "application/json");
                
                int status = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close();
                con.disconnect();
                
                JOptionPane.showMessageDialog(null, "Odgovor servera:\n" + response.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Greška: " + ex.getMessage());
            }
            return null;
        }
    }

    private class SwingWorkerImpl1 extends SwingWorker<Void, Void> {

        public SwingWorkerImpl1() {
        }

        @Override
        protected Void doInBackground() {
            try {
                String urlStr = BASE_URL + "/dohvati/korisnike";
                HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Accept", "application/json");
                
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close();
                con.disconnect();
                
                updateTable(response.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Greška pri dohvatanju korisnika: " + ex.getMessage());
            }
            return null;
        }
    }
}