package com.mycompany.clientapplicationwithswing.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class OcenaPanel extends JPanel {

    private JTextField korisnikIdField = new JTextField(5);
    private JTextField audioIdField = new JTextField(5);
    private JTextField vrednostField = new JTextField(3);
    private JTextField vremeField = new JTextField(12);
    private JTextField novaVrednostField = new JTextField(3);
    private JTextField prikazAudioIdField = new JTextField(5);

    private JButton dodajBtn = new JButton("Dodaj ocenu");
    private JButton izmeniBtn = new JButton("Izmeni ocenu");
    private JButton obrisiBtn = new JButton("Obriši ocenu");
    private JButton prikaziOceneBtn = new JButton("Prikaži ocene");

    private JTable tabelaOcena;
    private DefaultTableModel modelOcena = new DefaultTableModel(new String[]{"Korisnik ID", "Ocena", "Vreme"}, 0);
    private JLabel prosekLabel = new JLabel(" ");
    private JLabel statusLabel = new JLabel(" ");

    private static final String BASE_URL = "http://localhost:8080/CentralniServer/ocena";
    private static final String DOHVATI_URL = "http://localhost:8080/CentralniServer/dohvati/ocene/";

    public OcenaPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 📋 Forma
        JPanel forma = new JPanel(new GridLayout(4, 1, 5, 5));
        forma.setBorder(BorderFactory.createTitledBorder("⭐ Upravljanje ocenama"));

        JPanel red1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red1.add(new JLabel("Korisnik ID:")); red1.add(korisnikIdField);
        red1.add(new JLabel("Audio ID:")); red1.add(audioIdField);
        red1.add(new JLabel("Vrednost:")); red1.add(vrednostField);
        red1.add(new JLabel("Vreme:")); red1.add(vremeField);
        red1.add(dodajBtn);

        JPanel red2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red2.add(new JLabel("Nova vrednost:")); red2.add(novaVrednostField);
        red2.add(izmeniBtn);

        JPanel red3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red3.add(obrisiBtn);

        JPanel red4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red4.add(new JLabel("Audio ID za pregled:")); red4.add(prikazAudioIdField);
        red4.add(prikaziOceneBtn);

        forma.add(red1); forma.add(red2); forma.add(red3); forma.add(red4);

        // 📊 Tabela i Prosek
        JPanel prikazPanel = new JPanel(new BorderLayout());
        prikazPanel.setBorder(BorderFactory.createTitledBorder("📊 Ocene za audio snimak"));

        tabelaOcena = new JTable(modelOcena);
        JScrollPane scrollOcene = new JScrollPane(tabelaOcena);
        prikazPanel.add(scrollOcene, BorderLayout.CENTER);
        prikazPanel.add(prosekLabel, BorderLayout.SOUTH);

        // 🔔 Status
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("📢 Status"));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // 🧩 Sastavi panel
        add(forma, BorderLayout.NORTH);
        add(prikazPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        // 🎛️ Akcije
        dodajBtn.addActionListener(e -> dodajOcenu());
        izmeniBtn.addActionListener(e -> izmeniOcenu());
        obrisiBtn.addActionListener(e -> obrisiOcenu());
        prikaziOceneBtn.addActionListener(e -> dohvatiOceneAudia());
    }

    private void prikaziPoruku(String tekst, Color boja) {
        statusLabel.setText(tekst);
        statusLabel.setForeground(boja);
    }

    private void dodajOcenu() {
        String kId = korisnikIdField.getText().trim();
        String aId = audioIdField.getText().trim();
        String vrednost = vrednostField.getText().trim();
        String vreme = vremeField.getText().trim();

        if (kId.isEmpty() || aId.isEmpty() || vrednost.isEmpty() || vreme.isEmpty()) {
            prikaziPoruku("❌ Popuni sva polja!", Color.RED);
            return;
        }

        try {
            int v = Integer.parseInt(vrednost);
            if (v < 1 || v > 5) throw new IllegalArgumentException();
            String url = BASE_URL + "/kreiraj/" + kId + "/" + aId + "/" + v + "/" + vreme;
            sendRequest("POST", url, "✅ Ocena dodata.");
        } catch (Exception ex) {
            prikaziPoruku("⚠️ Ocena mora biti 1–5.", Color.RED);
        }
    }

    private void izmeniOcenu() {
        String kId = korisnikIdField.getText().trim();
        String aId = audioIdField.getText().trim();
        String novaOcena = novaVrednostField.getText().trim();

        if (kId.isEmpty() || aId.isEmpty() || novaOcena.isEmpty()) {
            prikaziPoruku("❌ Popuni korisnik ID, audio ID i novu ocenu.", Color.RED);
            return;
        }

        try {
            int novaV = Integer.parseInt(novaOcena);
            if (novaV < 1 || novaV > 5) throw new IllegalArgumentException();
            String url = BASE_URL + "/izmeni/" + kId + "/" + aId + "/" + novaV;
            sendRequest("PUT", url, "✅ Ocena izmenjena.");
        } catch (Exception ex) {
            prikaziPoruku("⚠️ Ocena mora biti 1–5.", Color.RED);
        }
    }

    private void obrisiOcenu() {
        String kId = korisnikIdField.getText().trim();
        String aId = audioIdField.getText().trim();
        if (kId.isEmpty() || aId.isEmpty()) {
            prikaziPoruku("❌ Unesi korisnik ID i audio ID.", Color.RED);
            return;
        }
        String url = BASE_URL + "/obrisi/" + aId + "/" + kId;
        sendRequest("DELETE", url, "✅ Ocena obrisana.");
    }

    private void sendRequest(String method, String urlStr, String successMsg) {
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod(method);
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String jsonTxt = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close(); con.disconnect();

                    JSONObject json = new JSONObject(jsonTxt);
                    String status = json.optString("status");
                    String poruka = json.optString("poruka");

                    SwingUtilities.invokeLater(() -> {
                        if (status.equalsIgnoreCase("OK")) {
                            prikaziPoruku(successMsg, new Color(0, 128, 0));
                            korisnikIdField.setText(""); audioIdField.setText("");
                            vrednostField.setText(""); vremeField.setText("");
                            novaVrednostField.setText("");
                            dohvatiOceneAudia();
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

    private void dohvatiOceneAudia() {
        String idStr = prikazAudioIdField.getText().trim();
        if (idStr.isEmpty()) {
            prikaziPoruku("❌ Unesi audio ID za prikaz.", Color.RED);
            return;
        }

        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    String url = DOHVATI_URL + idStr;
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String response = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close(); con.disconnect();

                    JSONObject obj = new JSONObject(response);
                    JSONArray ocene = obj.optJSONArray("data");

                    SwingUtilities.invokeLater(() -> {
                        modelOcena.setRowCount(0);
                        double zbir = 0;
                        for (int i = 0; i < ocene.length(); i++) {
                            JSONObject o = ocene.getJSONObject(i);
                            int korisnikId = o.optInt("korisnikId");
                            int vrednost = o.optInt("vrednost");
                            String vreme = o.optString("vreme");

                            zbir += vrednost;
                            modelOcena.addRow(new Object[]{korisnikId, vrednost, vreme});
                        }

                        int count = ocene.length();
                        if (count > 0) {
                            double prosek = zbir / count;
                            String emoji = prosek >= 4 ? "😍" : prosek >= 3 ? "😊" : prosek >= 2 ? "😐" : "😞";
                            prosekLabel.setText(String.format("Prosečna ocena: %.2f %s (%d ocena)", prosek, emoji, count));
                            prosekLabel.setForeground(Color.BLUE);
                        } else {
                            prosekLabel.setText("Nema ocena za ovaj snimak.");
                            prosekLabel.setForeground(Color.GRAY);
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