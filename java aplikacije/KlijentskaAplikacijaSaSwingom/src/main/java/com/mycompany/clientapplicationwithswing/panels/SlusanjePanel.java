package com.mycompany.clientapplicationwithswing.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class SlusanjePanel extends JPanel {

    private JTable tabelaSlusanja;
    private DefaultTableModel model;

    private JTextField audioIdField, korisnikIdField, vremeField, pocetakField, trajanjeField;
    private JButton dugmeKreiraj, dugmeOsvezi;

    public SlusanjePanel() {
        setLayout(new BorderLayout());

        // Tabela
        model = new DefaultTableModel(new Object[]{"ID","Korisnik ID", "Vreme Početka", "Početni Sekund", "Trajanje"}, 0);
        tabelaSlusanja = new JTable(model);
        add(new JScrollPane(tabelaSlusanja), BorderLayout.CENTER);

        // Kontrole
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        audioIdField = new JTextField(); korisnikIdField = new JTextField();
        vremeField = new JTextField("2025-07-28T15:00:00"); // ISO format primer
        pocetakField = new JTextField(); trajanjeField = new JTextField();

        formPanel.add(new JLabel("Audio ID")); formPanel.add(new JLabel("Korisnik ID"));
        formPanel.add(new JLabel("Vreme Početka")); formPanel.add(new JLabel("Početni Sekund")); formPanel.add(new JLabel("Trajanje"));
        formPanel.add(audioIdField); formPanel.add(korisnikIdField);
        formPanel.add(vremeField); formPanel.add(pocetakField); formPanel.add(trajanjeField);
        add(formPanel, BorderLayout.NORTH);

        // Dugmad
        JPanel dugmadPanel = new JPanel();
        dugmeKreiraj = new JButton("Kreiraj slušanje");
        dugmeOsvezi = new JButton("Prikaži slušanja za Audio ID");
        dugmadPanel.add(dugmeKreiraj);
        dugmadPanel.add(dugmeOsvezi);
        add(dugmadPanel, BorderLayout.SOUTH);

        // Akcije
        dugmeKreiraj.addActionListener(e -> kreirajSlusanje());
        dugmeOsvezi.addActionListener(e -> {
            String idStr = audioIdField.getText().trim();
            if (!idStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Unesite validan Audio ID");
                return;
            }
            ucitajSlusanja(Integer.parseInt(idStr));
        });
    }

    private void kreirajSlusanje() {
        try {
            int korisnikId = Integer.parseInt(korisnikIdField.getText().trim());
            int audioId = Integer.parseInt(audioIdField.getText().trim());
            String vreme = vremeField.getText().trim();
            int pocetniSekund = Integer.parseInt(pocetakField.getText().trim());
            int trajanje = Integer.parseInt(trajanjeField.getText().trim());

            URL url = new URL("http://localhost:8080/CentralniServer/slusanje/kreiraj/"
                    + korisnikId + "/" + audioId + "/" + vreme + "/" + pocetniSekund + "/" + trajanje);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(0); os.flush(); os.close(); // prazno telo, jer podatke šaljemo u URL

            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 204) {
                JOptionPane.showMessageDialog(this, "Slušanje uspešno kreirano");
                ucitajSlusanja(audioId); // automatsko osvežavanje
            } else {
                JOptionPane.showMessageDialog(this, "Greška: " + responseCode);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Došlo je do greške: " + ex.getMessage());
        }
    }

    private void ucitajSlusanja(int audioId) {
        try {
            URL url = new URL("http://localhost:8080/CentralniServer/dohvati/slusanja/" + audioId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int kod = conn.getResponseCode();
            if (kod != 200) {
                JOptionPane.showMessageDialog(this, "Greška pri učitavanju: " + kod);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String linija;
            while ((linija = reader.readLine()) != null) {
                sb.append(linija);
            }

            JSONObject odgovor = new JSONObject(sb.toString());
            JSONArray podaci = odgovor.getJSONArray("data");

            model.setRowCount(0);
            for (int i = 0; i < podaci.length(); i++) {
                JSONObject obj = podaci.getJSONObject(i);
                model.addRow(new Object[]{
                    obj.getInt("id"),
                    obj.getInt("korisnikId"),
                    obj.getString("vremePocetka"),
                    obj.getInt("pocetniSekund"),
                    obj.getInt("trajanje")
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage());
        }
    }
}