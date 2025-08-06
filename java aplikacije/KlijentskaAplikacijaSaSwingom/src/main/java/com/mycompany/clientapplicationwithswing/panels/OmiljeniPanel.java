package com.mycompany.clientapplicationwithswing.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class OmiljeniPanel extends JPanel {

    private JTextField korisnikIdField = new JTextField(5);
    private JTextField audioIdField = new JTextField(5);
    private JButton dodajBtn = new JButton("Dodaj u omiljene");

    private JTextField prikazKorisnikIdField = new JTextField(5);
    private JButton prikaziBtn = new JButton("Prikaži omiljene");

    private JTable tabelaOmiljeni;
    private DefaultTableModel modelOmiljeni = new DefaultTableModel(new String[]{"Audio ID", "Naziv", "Opis"}, 0);

    private JLabel statusLabel = new JLabel(" ");

    private static final String BASE_URL = "http://localhost:8080/CentralniServer/omiljeni";
    private static final String DOHVATI_URL = "http://localhost:8080/CentralniServer/dohvati/omiljene/";

    public OmiljeniPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 📋 Forma
        JPanel forma = new JPanel(new GridLayout(2, 1, 5, 5));
        forma.setBorder(BorderFactory.createTitledBorder("❤️ Upravljanje omiljenim"));

        JPanel red1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red1.add(new JLabel("Korisnik ID:")); red1.add(korisnikIdField);
        red1.add(new JLabel("Audio ID:")); red1.add(audioIdField);
        red1.add(dodajBtn);

        JPanel red2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red2.add(new JLabel("Korisnik ID za prikaz:")); red2.add(prikazKorisnikIdField);
        red2.add(prikaziBtn);

        forma.add(red1); forma.add(red2);

        // 📊 Tabela
        tabelaOmiljeni = new JTable(modelOmiljeni);
        JScrollPane scrollPane = new JScrollPane(tabelaOmiljeni);
        scrollPane.setBorder(BorderFactory.createTitledBorder("🎵 Lista omiljenih audio snimaka"));

        // 🔔 Status
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("📢 Status"));
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // 🧩 Sastavi panel
        add(forma, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        // 🎛️ Akcije
        dodajBtn.addActionListener(e -> dodajOmiljeni());
        prikaziBtn.addActionListener(e -> dohvatiOmiljene());
    }

    private void prikaziPoruku(String tekst, Color boja) {
        statusLabel.setText(tekst);
        statusLabel.setForeground(boja);
    }

    private void dodajOmiljeni() {
        String kId = korisnikIdField.getText().trim();
        String aId = audioIdField.getText().trim();
        if (kId.isEmpty() || aId.isEmpty()) {
            prikaziPoruku("❌ Popuni ID korisnika i audio ID.", Color.RED);
            return;
        }

        String url = BASE_URL + "/dodaj/" + kId + "/" + aId;
        sendRequest("POST", url, "✅ Audio snimak dodat u omiljene.");
    }

    private void sendRequest(String method, String urlStr, String successMsg) {
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod(method);
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String response = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close(); con.disconnect();

                    JSONObject json = new JSONObject(response);
                    String status = json.optString("status");
                    String poruka = json.optString("data");

                    SwingUtilities.invokeLater(() -> {
                        if (status.equalsIgnoreCase("OK")) {
                            prikaziPoruku(successMsg, new Color(0, 128, 0));
                            korisnikIdField.setText(""); audioIdField.setText("");
                            dohvatiOmiljene();
                        } else {
                            prikaziPoruku("❌ " + poruka, Color.RED);
                        }
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                        prikaziPoruku("⚠️ Greška: " + ex.getMessage(), Color.ORANGE));
                }
                return null;
            }
        }.execute();
    }

    private void dohvatiOmiljene() {
        String kId = prikazKorisnikIdField.getText().trim();
        if (kId.isEmpty()) {
            prikaziPoruku("❌ Unesi korisnik ID za prikaz.", Color.RED);
            return;
        }

        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    String url = DOHVATI_URL + kId;
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String response = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close(); con.disconnect();

                    JSONObject obj = new JSONObject(response);
                    JSONArray omiljeni = obj.optJSONArray("data");

                    SwingUtilities.invokeLater(() -> {
                        modelOmiljeni.setRowCount(0);
                        for (int i = 0; i < omiljeni.length(); i++) {
                            JSONObject a = omiljeni.getJSONObject(i);
                            modelOmiljeni.addRow(new Object[]{
                                    a.optInt("audioId"),
                                    a.optString("naziv"),
                                    a.optString("opis")
                            });
                        }

                        if (omiljeni.length() == 0) {
                            prikaziPoruku("ℹ️ Korisnik nema omiljenih snimaka.", Color.GRAY);
                        } else {
                            prikaziPoruku("✅ Omiljeni snimci učitani.", new Color(0, 128, 0));
                        }
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                        prikaziPoruku("⚠️ Greška: " + ex.getMessage(), Color.ORANGE));
                }
                return null;
            }
        }.execute();
    }
}