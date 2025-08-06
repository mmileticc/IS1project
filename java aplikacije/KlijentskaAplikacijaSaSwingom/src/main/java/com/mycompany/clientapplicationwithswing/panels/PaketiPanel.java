package com.mycompany.clientapplicationwithswing.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class PaketiPanel extends JPanel {

    private JTextField nazivField = new JTextField(10);
    private JTextField cenaField = new JTextField(5);
    private JTextField idPaketaField = new JTextField(5);
    private JTextField novaCenaField = new JTextField(5);

    private JButton kreirajBtn = new JButton("Kreiraj paket");
    private JButton promeniBtn = new JButton("Promeni cenu");
    private JButton prikaziBtn = new JButton("Prikaži sve pakete");

    private JTable tabelaPaketa;
    private DefaultTableModel modelPaketi;

    private JLabel statusLabel = new JLabel(" ");

    private static final String BASE_URL = "http://localhost:8080/CentralniServer/paket";
    private static final String BASE_URL_DOHVATI = "http://localhost:8080/CentralniServer/dohvati/pakete";

    public PaketiPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 📋 Forma
        JPanel forma = new JPanel(new GridLayout(3, 1, 5, 5));
        forma.setBorder(BorderFactory.createTitledBorder("🛠️ Upravljanje paketima"));

        JPanel red1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red1.add(new JLabel("Naziv:")); red1.add(nazivField);
        red1.add(new JLabel("Cena:")); red1.add(cenaField);
        red1.add(kreirajBtn);

        JPanel red2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red2.add(new JLabel("ID paketa:")); red2.add(idPaketaField);
        red2.add(new JLabel("Nova cena:")); red2.add(novaCenaField);
        red2.add(promeniBtn);

        JPanel red3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        red3.add(prikaziBtn);

        forma.add(red1); forma.add(red2); forma.add(red3);

        // 📊 Tabela
        modelPaketi = new DefaultTableModel(new String[]{"ID", "Naziv", "Cena"}, 0);
        tabelaPaketa = new JTable(modelPaketi);
        JScrollPane scrollPane = new JScrollPane(tabelaPaketa);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📦 Lista paketa"));

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
        kreirajBtn.addActionListener(e -> kreirajPaket());
        promeniBtn.addActionListener(e -> promeniCenu());
        prikaziBtn.addActionListener(e -> dohvatiPakete());
    }

    private void prikaziPoruku(String tekst, Color boja) {
        statusLabel.setText(tekst);
        statusLabel.setForeground(boja);
    }

    private void kreirajPaket() {
        String naziv = nazivField.getText().trim();
        String cenaStr = cenaField.getText().trim();
        if (naziv.isEmpty() || cenaStr.isEmpty()) {
            prikaziPoruku("❌ Unesi naziv i cenu.", Color.RED);
            return;
        }

        try {
            double cena = Double.parseDouble(cenaStr);
            String url = BASE_URL + "/kreiraj/" + naziv + "/" + cena;
            sendRequest("POST", url, "Paket kreiran.");
        } catch (NumberFormatException ex) {
            prikaziPoruku("⚠️ Cena mora biti broj.", Color.RED);
        }
    }

    private void promeniCenu() {
        String idStr = idPaketaField.getText().trim();
        String novaCenaStr = novaCenaField.getText().trim();
        if (idStr.isEmpty() || novaCenaStr.isEmpty()) {
            prikaziPoruku("❌ Unesi ID i novu cenu.", Color.RED);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double novaCena = Double.parseDouble(novaCenaStr);
            String url = BASE_URL + "/promeni/cenu/" + id + "/" + novaCena;
            sendRequest("PUT", url, "Cena paketa promenjena.");
        } catch (NumberFormatException ex) {
            prikaziPoruku("⚠️ Neispravan unos.", Color.RED);
        }
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
                    reader.close();
                    con.disconnect();

                    JSONObject json = new JSONObject(jsonTxt);
                    String status = json.optString("status");
                    String poruka = json.optString("poruka");

                    SwingUtilities.invokeLater(() -> {
                        if (status.equalsIgnoreCase("OK")) {
                            prikaziPoruku("✅ " + poruka, new Color(0, 128, 0));
                            nazivField.setText(""); cenaField.setText("");
                            idPaketaField.setText(""); novaCenaField.setText("");
                            dohvatiPakete();
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
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(BASE_URL_DOHVATI).openConnection();
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
                        prikaziPoruku("❌ Greška pri dohvatanju paketa: " + ex.getMessage(), Color.RED));
                }
                return null;
            }
        }.execute();
    }
}