package com.mycompany.clientapplicationwithswing.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

public class MestoPanel extends JPanel {

    private JTextField imeField = new JTextField(20);
    private JButton kreirajButton = new JButton("Kreiraj mesto");
    private JButton prikaziButton = new JButton("Prikaži sva mesta");

    private JLabel statusLabel = new JLabel(" ");

    private JTable tabelaMesta;
    private DefaultTableModel tableModel;

    private static final String BASE_URL = "http://localhost:8080/CentralniServer";

    public MestoPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 📦 Forma panel
        JPanel forma = new JPanel(new GridBagLayout());
        forma.setBorder(BorderFactory.createTitledBorder("🛠️ Kreiranje mesta"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        forma.add(new JLabel("Naziv mesta:"), gbc);

        gbc.gridx = 1;
        forma.add(imeField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        forma.add(kreirajButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        forma.add(prikaziButton, gbc);

        kreirajButton.addActionListener(e -> posaljiZahtev());
        prikaziButton.addActionListener(e -> dohvatiSvaMesta());

        // 🔔 Status poruka
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setForeground(Color.DARK_GRAY);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("📢 Status"));
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // 📊 Tabela
        String[] kolone = { "ID", "Naziv" };
        tableModel = new DefaultTableModel(kolone, 0);
        tabelaMesta = new JTable(tableModel);
        JScrollPane tabelaScroll = new JScrollPane(tabelaMesta);
        tabelaScroll.setBorder(BorderFactory.createTitledBorder("📋 Lista svih mesta"));

        // 🧩 Sastavljanje
        add(forma, BorderLayout.NORTH);
        add(tabelaScroll, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void posaljiZahtev() {
        String naziv = imeField.getText().trim();
        if (naziv.isEmpty()) {
            prikaziPoruku("❌ Naziv mesta ne može biti prazan.", Color.RED);
            return;
        }

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    String urlStr = BASE_URL + "/mesto/kreiraj/" + URLEncoder.encode(naziv, "UTF-8");
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    con.disconnect();

                    JSONObject odgovor = new JSONObject(response.toString());
                    String statusStr = odgovor.optString("status");
                    String poruka = odgovor.optString("poruka");

                    SwingUtilities.invokeLater(() -> {
                        if (statusStr.equalsIgnoreCase("OK")) {
                            prikaziPoruku("✅ " + poruka, new Color(0, 128, 0));
                            imeField.setText("");
                            dohvatiSvaMesta(); // automatsko osvežavanje tabele
                        } else {
                            prikaziPoruku("❌ Greška: " + poruka, Color.RED);
                        }
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            prikaziPoruku("⚠️ Greška pri komunikaciji: " + ex.getMessage(), Color.ORANGE));
                }
                return null;
            }
        }.execute();
    }

    private void dohvatiSvaMesta() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    String urlStr = BASE_URL + "/dohvati/mesta";
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    con.disconnect();

                    JSONObject odgovor = new JSONObject(response.toString());
                    JSONArray mesta = odgovor.optJSONArray("data"); // Prilagodi ako je drugačiji ključ

                    SwingUtilities.invokeLater(() -> {
                        tableModel.setRowCount(0); // Clear table
                        for (int i = 0; i < mesta.length(); i++) {
                            JSONObject m = mesta.getJSONObject(i);
                            int id = m.optInt("id");
                            String naziv = m.optString("naziv");
                            tableModel.addRow(new Object[]{id, naziv});
                        }
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            prikaziPoruku("❌ Greška pri dohvatanju mesta: " + ex.getMessage(), Color.RED));
                }
                return null;
            }
        }.execute();
    }

    private void prikaziPoruku(String poruka, Color boja) {
        statusLabel.setText(poruka);
        statusLabel.setForeground(boja);
    }
}