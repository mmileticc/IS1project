package com.mycompany.clientapplicationwithswing.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class PretplataPanel extends JPanel {

    private JTextField korisnikIdField = new JTextField(5);
    private JTextField paketIdField = new JTextField(5);
    private JTextField korisnikZaPretplateField = new JTextField(5);

    private JButton kreirajBtn = new JButton("Kreiraj pretplatu");
    private JButton prikaziPaketeBtn = new JButton("Prikaži sve pakete");
    private JButton prikaziPretplateBtn = new JButton("Pretplate korisnika");

    private JTable tabelaPaketa;
    private JTable tabelaPretplata;
    private DefaultTableModel modelPaketi;
    private DefaultTableModel modelPretplate;

    private JLabel statusLabel = new JLabel(" ");

    private static final String BASE_URL_PAKET = "http://localhost:8080/CentralniServer/paket";
    private static final String BASE_URL_DOHVATI = "http://localhost:8080/CentralniServer/dohvati";

    public PretplataPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🛠️ Forma za kreiranje
        JPanel kreiranje = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kreiranje.setBorder(BorderFactory.createTitledBorder("🧾 Nova pretplata"));

        kreiranje.add(new JLabel("ID korisnika:"));
        kreiranje.add(korisnikIdField);
        kreiranje.add(new JLabel("ID paketa:"));
        kreiranje.add(paketIdField);
        kreiranje.add(kreirajBtn);

        // 🔎 Dohvatanje
        JPanel prikazPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        prikazPanel.setBorder(BorderFactory.createTitledBorder("📥 Prikaz podataka"));

        prikazPanel.add(prikaziPaketeBtn);
        prikazPanel.add(new JLabel("ID korisnika:"));
        prikazPanel.add(korisnikZaPretplateField);
        prikazPanel.add(prikaziPretplateBtn);

        // 📊 Tabele
        modelPaketi = new DefaultTableModel(new String[]{"ID", "Naziv", "Cena"}, 0);
        tabelaPaketa = new JTable(modelPaketi);
        JScrollPane scrollPaketi = new JScrollPane(tabelaPaketa);
        scrollPaketi.setBorder(BorderFactory.createTitledBorder("📦 Paketi"));

        modelPretplate = new DefaultTableModel(new String[]{"ID", "Naziv paketa", "Cena"}, 0);
        tabelaPretplata = new JTable(modelPretplate);
        JScrollPane scrollPretplate = new JScrollPane(tabelaPretplata);
        scrollPretplate.setBorder(BorderFactory.createTitledBorder("🔐 Pretplate korisnika"));

        // 🔔 Status
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("📢 Status"));
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // 🧩 Sastavi panel
        JPanel gornji = new JPanel(new GridLayout(2, 1));
        gornji.add(kreiranje);
        gornji.add(prikazPanel);

        JPanel centar = new JPanel(new GridLayout(1, 2));
        centar.add(scrollPaketi);
        centar.add(scrollPretplate);

        add(gornji, BorderLayout.NORTH);
        add(centar, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        // 🎛️ Akcije
        kreirajBtn.addActionListener(e -> kreirajPretplatu());
        prikaziPaketeBtn.addActionListener(e -> dohvatiPakete());
        prikaziPretplateBtn.addActionListener(e -> dohvatiPretplate());
    }

    private void prikaziPoruku(String tekst, Color boja) {
        statusLabel.setText(tekst);
        statusLabel.setForeground(boja);
    }

    private void kreirajPretplatu() {
        String idK = korisnikIdField.getText().trim();
        String idP = paketIdField.getText().trim();

        if (idK.isEmpty() || idP.isEmpty()) {
            prikaziPoruku("❌ Unesi ID korisnika i ID paketa.", Color.RED);
            return;
        }

        String url = BASE_URL_PAKET + "/kreiraj/pretplatu/" + idK + "/" + idP;

        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String jsonTxt = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close();
                    con.disconnect();

                    JSONObject odgovor = new JSONObject(jsonTxt);
                    String status = odgovor.optString("status");
                    String poruka = odgovor.optString("poruka");

                    SwingUtilities.invokeLater(() -> {
                        if (status.equalsIgnoreCase("OK")) {
                            prikaziPoruku("✅ " + poruka, new Color(0, 128, 0));
                            korisnikIdField.setText(""); paketIdField.setText("");
                            dohvatiPretplate(); // automatski osveži
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

    private void dohvatiPakete() {
        String url = BASE_URL_DOHVATI + "/pakete";

        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String response = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close();
                    con.disconnect();

                    JSONObject obj = new JSONObject(response);
                    JSONArray paketi = obj.optJSONArray("data");

                    SwingUtilities.invokeLater(() -> {
                        modelPaketi.setRowCount(0);
                        for (int i = 0; i < paketi.length(); i++) {
                            JSONObject p = paketi.getJSONObject(i);
                            modelPaketi.addRow(new Object[]{
                                    p.optInt("id"),
                                    p.optString("naziv"),
                                    p.optDouble("cena")
                            });
                        }
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            prikaziPoruku("❌ Greška pri dohvaćanju paketa: " + ex.getMessage(), Color.RED));
                }
                return null;
            }
        }.execute();
    }

    private void dohvatiPretplate() {
        String korisnikId = korisnikZaPretplateField.getText().trim();
        if (korisnikId.isEmpty()) {
            prikaziPoruku("❌ Unesi ID korisnika.", Color.RED);
            return;
        }

        String url = BASE_URL_DOHVATI + "/pretplate/" + korisnikId;

        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String response = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close();
                    con.disconnect();

                    JSONObject obj = new JSONObject(response);
                    JSONArray pretplate = obj.optJSONArray("data");

                    SwingUtilities.invokeLater(() -> {
                        modelPretplate.setRowCount(0);
                        for (int i = 0; i < pretplate.length(); i++) {
                            JSONObject p = pretplate.getJSONObject(i);
                            modelPretplate.addRow(new Object[]{
                                p.optInt("id"),
                                p.optString("nazivPaketa"),
                                p.optDouble("cena")
                            });
                        }
                    });
               
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                        prikaziPoruku("⚠️ Greška pri dohvaćanju pretplata: " + ex.getMessage(), Color.ORANGE));
                }
                return null;
            }
        }.execute();
    }
}