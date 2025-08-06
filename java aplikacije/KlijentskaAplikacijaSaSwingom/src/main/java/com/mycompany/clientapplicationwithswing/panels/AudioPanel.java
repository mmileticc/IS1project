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

public class AudioPanel extends JPanel {

    // 🔹 Polja za kreiranje
    private JTextField nazivField = new JTextField(15);
    private JTextField trajanjeField = new JTextField(5);
    private JTextField datumField = new JTextField(10);
    private JTextField vlasnikIdField = new JTextField(5);

    // 🔹 Polja za izmenu
    private JTextField idIzmenaField = new JTextField(5);
    private JTextField novoImeField = new JTextField(15);

    // 🔹 Polja za dodavanje kategorije
    private JTextField audioIdKatField = new JTextField(5);
    private JTextField kategorijaIdField = new JTextField(5);

    // 🔹 Polja za brisanje
    private JTextField audioIdBrisanjeField = new JTextField(5);
    private JTextField korisnikIdField = new JTextField(5);

    private JButton kreirajBtn = new JButton("Kreiraj snimak");
    private JButton prikaziBtn = new JButton("Prikaži sve snimke");
    private JButton promeniBtn = new JButton("Promeni ime");
    private JButton dodajKatBtn = new JButton("Dodaj kategoriju");
    private JButton obrisiBtn = new JButton("Obriši snimak");

    private JLabel statusLabel = new JLabel(" ");

    private JTable tabelaAudio;
    private DefaultTableModel tableModel;
    
    // Kreiranje kategorije
    private JTextField nazivKategorijeField = new JTextField(15);
    private JButton kreirajKategorijuBtn = new JButton("Kreiraj kategoriju");

    // Tabela svih kategorija
    private JTable tabelaKategorije;
    private DefaultTableModel modelKategorije;
    private JButton prikaziKategorijeBtn = new JButton("Prikaži sve kategorije");

    // Tabela kategorija za audio snimak
    private JTable tabelaAudioKategorije;
    private DefaultTableModel modelAudioKategorije;
    private JTextField audioIdZaKategorijeField = new JTextField(5);
    private JButton prikaziKategorijeZaAudioBtn = new JButton("Kategorije za snimak");

    private static final String BASE_URL = "http://localhost:8080/CentralniServer";

    public AudioPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 📋 Tabela
        String[] kolone = { "ID", "Naziv", "Trajanje", "Datum", "IDVlasnika" };
        tableModel = new DefaultTableModel(kolone, 0);
        tabelaAudio = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaAudio);
        scrollPane.setBorder(BorderFactory.createTitledBorder("🎙️ Audio snimci"));
        
        String[] koloneKat = { "ID", "Naziv" };
        modelKategorije = new DefaultTableModel(koloneKat, 0);
        tabelaKategorije = new JTable(modelKategorije);
        JScrollPane scrollKat = new JScrollPane(tabelaKategorije);
        scrollKat.setBorder(BorderFactory.createTitledBorder("📋 Sve kategorije"));
        
        String[] koloneAK = { "ID", "Naziv kategorije" };
        modelAudioKategorije = new DefaultTableModel(koloneAK, 0);
        tabelaAudioKategorije = new JTable(modelAudioKategorije);
        JScrollPane scrollAK = new JScrollPane(tabelaAudioKategorije);
        scrollAK.setBorder(BorderFactory.createTitledBorder("🎼 Kategorije snimka"));

        // 🛠️ Panel za forme
        JPanel formePanel = new JPanel(new GridLayout(8, 1, 5, 5));
        formePanel.setBorder(BorderFactory.createTitledBorder("🧩 Akcije"));

        // Kreiranje
        JPanel kreiranje = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kreiranje.add(new JLabel("Naziv:")); kreiranje.add(nazivField);
        kreiranje.add(new JLabel("Trajanje:")); kreiranje.add(trajanjeField);
        kreiranje.add(new JLabel("Datum:")); kreiranje.add(datumField);
        kreiranje.add(new JLabel("ID Vlasnika:")); kreiranje.add(vlasnikIdField);
        kreiranje.add(kreirajBtn);

        // Izmena
        JPanel izmena = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izmena.add(new JLabel("ID Snimka:")); izmena.add(idIzmenaField);
        izmena.add(new JLabel("Novo ime:")); izmena.add(novoImeField);
        izmena.add(promeniBtn);

        // Dodavanje kategorije
        JPanel dodavanjeKat = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dodavanjeKat.add(new JLabel("Audio ID:")); dodavanjeKat.add(audioIdKatField);
        dodavanjeKat.add(new JLabel("Kategorija ID:")); dodavanjeKat.add(kategorijaIdField);
        dodavanjeKat.add(dodajKatBtn);

        // Brisanje
        JPanel brisanje = new JPanel(new FlowLayout(FlowLayout.LEFT));
        brisanje.add(new JLabel("Audio ID:")); brisanje.add(audioIdBrisanjeField);
        brisanje.add(new JLabel("Korisnik ID:")); brisanje.add(korisnikIdField);
        brisanje.add(obrisiBtn);

        // Status & prikaz
        JPanel prikaz = new JPanel(new FlowLayout(FlowLayout.LEFT));
        prikaz.add(prikaziBtn);

        // Kreiranje kategorije
        JPanel katPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        katPanel.add(new JLabel("Naziv kategorije:"));
        katPanel.add(nazivKategorijeField);
        katPanel.add(kreirajKategorijuBtn);

        // Dohvatanje kategorija
        JPanel dohvatiKatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dohvatiKatPanel.add(prikaziKategorijeBtn);
        
        
        JPanel dohvKatSnimkaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dohvKatSnimkaPanel.add(new JLabel("Audio ID:"));
        dohvKatSnimkaPanel.add(audioIdZaKategorijeField);
        dohvKatSnimkaPanel.add(prikaziKategorijeZaAudioBtn);
        
        formePanel.add(kreiranje);
        formePanel.add(izmena);
        formePanel.add(dodavanjeKat);
        formePanel.add(brisanje);
        formePanel.add(prikaz);
        formePanel.add(katPanel);
        formePanel.add(dohvatiKatPanel);
        formePanel.add(dohvKatSnimkaPanel);
        // Status
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("📢 Status"));
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // 🧩 Sastavi
        add(formePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(scrollKat, BorderLayout.EAST); // ili u drugi panel ako koristiš SplitPane
        add(scrollAK, BorderLayout.WEST); // ili u drugi tab/panel
        add(statusPanel, BorderLayout.SOUTH);

        // 🖱️ Akcije
        kreirajBtn.addActionListener(e -> kreiraj());
        promeniBtn.addActionListener(e -> promeniIme());
        dodajKatBtn.addActionListener(e -> dodajKategoriju());
        obrisiBtn.addActionListener(e -> obrisi());
        prikaziBtn.addActionListener(e -> prikaziSve());
        kreirajKategorijuBtn.addActionListener(e -> kreirajKategoriju());
        prikaziKategorijeBtn.addActionListener(e -> prikaziSveKategorije());
        prikaziKategorijeZaAudioBtn.addActionListener(e -> prikaziKategorijeZaAudio());
    }

    private void prikaziPoruku(String poruka, Color boja) {
        statusLabel.setText(poruka);
        statusLabel.setForeground(boja);
    }
    private void kreirajKategoriju() {
        String naziv = nazivKategorijeField.getText().trim();
        if (naziv.isEmpty()) {
            prikaziPoruku("❌ Naziv kategorije je obavezan.", Color.RED);
            return;
        }

        String url = "http://localhost:8080/CentralniServer/kategorija/kreiraj/" + encode(naziv);
        sendPost(url, "Kategorija kreirana.");
    }
    private void prikaziSveKategorije() {
        String url = "http://localhost:8080/CentralniServer/dohvati/kategorije";
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

                    JSONObject json = new JSONObject(response);
                    JSONArray data = json.optJSONArray("data");

                    SwingUtilities.invokeLater(() -> {
                        modelKategorije.setRowCount(0);
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject k = data.getJSONObject(i);
                            modelKategorije.addRow(new Object[]{
                                k.optInt("id"), k.optString("naziv")
                            });
                        }
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> prikaziPoruku("❌ Greška pri dohvatanju kategorija: " + ex.getMessage(), Color.RED));
                }
                return null;
            }
        }.execute();
    }
    
    private void prikaziKategorijeZaAudio() {
        String audioId = audioIdZaKategorijeField.getText().trim();
        if (audioId.isEmpty()) {
            prikaziPoruku("❌ Unesi ID snimka.", Color.RED);
            return;
        }
        String url = "http://localhost:8080/CentralniServer/dohvati/kategorije/" + audioId;
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

                    JSONObject json = new JSONObject(response);
                    JSONArray data = json.optJSONArray("data");

                    SwingUtilities.invokeLater(() -> {
                        modelAudioKategorije.setRowCount(0);
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject k = data.getJSONObject(i);
                            modelAudioKategorije.addRow(new Object[]{
                                k.optInt("id"), k.optString("naziv")
                            });
                        }
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> prikaziPoruku("❌ Greška pri dohvatanju: " + ex.getMessage(), Color.RED));
                }
                return null;
            }
        }.execute();
    }
    

    private void kreiraj() {
        String naziv = nazivField.getText().trim();
        String trajanje = trajanjeField.getText().trim();
        String datum = datumField.getText().trim();
        String idV = vlasnikIdField.getText().trim();

        if (naziv.isEmpty() || trajanje.isEmpty() || datum.isEmpty() || idV.isEmpty()) {
            prikaziPoruku("❌ Sva polja su obavezna!", Color.RED);
            return;
        }

        String url = BASE_URL + "/audio/kreiraj/" + encode(datum) + "/" + encode(naziv) + "/" + trajanje + "/" + idV;
        sendPost(url, "Audio snimak kreiran.");
    }

    private void promeniIme() {
        String id = idIzmenaField.getText().trim();
        String novoIme = novoImeField.getText().trim();
        if (id.isEmpty() || novoIme.isEmpty()) {
            prikaziPoruku("❌ ID i novo ime su obavezni!", Color.RED);
            return;
        }

        String url = BASE_URL + "/audio/promeni/ime/" + id + "/" + encode(novoIme);
        sendPut(url, "Ime snimka promenjeno.");
    }

    private void dodajKategoriju() {
        String audioId = audioIdKatField.getText().trim();
        String katId = kategorijaIdField.getText().trim();
        if (audioId.isEmpty() || katId.isEmpty()) {
            prikaziPoruku("❌ ID snimka i kategorije su obavezni!", Color.RED);
            return;
        }

        String url = BASE_URL + "/audio/dodaj/kategoriju/" + audioId + "/" + katId;
        sendPut(url, "Kategorija dodata.");
    }

    private void obrisi() {
        String audioId = audioIdBrisanjeField.getText().trim();
        String korisnikId = korisnikIdField.getText().trim();
        if (audioId.isEmpty() || korisnikId.isEmpty()) {
            prikaziPoruku("❌ Potreban je audio ID i ID korisnika!", Color.RED);
            return;
        }

        String url = BASE_URL + "/audio/obrisi/" + audioId + "/" + korisnikId;
        sendDelete(url, "Audio snimak obrisan.");
    }

    private void prikaziSve() {
        String url = BASE_URL + "/dohvati/audio/snimke"; // pod pretpostavkom da API vraća JSONArray
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                    reader.close();
                    con.disconnect();

                    JSONObject root = new JSONObject(sb.toString());//
                    JSONArray arr = root.optJSONArray("data");//
                    
                    SwingUtilities.invokeLater(() -> {
                        tableModel.setRowCount(0);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject snimak = arr.getJSONObject(i);
                            tableModel.addRow(new Object[]{
                                snimak.optInt("id"),
                                snimak.optString("naziv"),
                                snimak.optInt("trajanje"),
                                snimak.optString("datum_postavljanja"),
                                snimak.optInt("idV")
                            });
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            prikaziPoruku("❌ Greška pri prikazu: " + ex.getMessage(), Color.RED));
                }
                return null;
            }
        }.execute();
    }

    private void sendPost(String urlStr, String successMsg) {
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String response = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close();
                    con.disconnect();

                    JSONObject json = new JSONObject(response);
                    String status = json.optString("status");
                    String poruka = json.optString("poruka");

                    SwingUtilities.invokeLater(() -> {
                        if (status.equalsIgnoreCase("OK")) {
                            prikaziPoruku("✅ " + poruka, new Color(0, 128, 0));
                            prikaziSve();
                        } else {
                            prikaziPoruku("❌ " + poruka, Color.RED);
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            prikaziPoruku("⚠️ " + ex.getMessage(), Color.ORANGE));
                }
                return null;
            }
        }.execute();
    }

    private void sendPut(String urlStr, String successMsg) {
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod("PUT");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String response = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close();
                    con.disconnect();

                    JSONObject json = new JSONObject(response);
                    String status = json.optString("status");
                    String poruka = json.optString("poruka");

                    SwingUtilities.invokeLater(() -> {
                        if (status.equalsIgnoreCase("OK")) {
                            prikaziPoruku("✅ " + poruka, new Color(0, 128, 0));
                            prikaziSve();
                        } else {
                            prikaziPoruku("❌ " + poruka, Color.RED);
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            prikaziPoruku("⚠️ " + ex.getMessage(), Color.ORANGE));
                }
                return null;
            }
        }.execute();
    }

    private void sendDelete(String urlStr, String successMsg) {
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod("DELETE");
                    con.setRequestProperty("Accept", "application/json");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String response = reader.lines().reduce("", (acc, line) -> acc + line);
                    reader.close();
                    con.disconnect();

                    JSONObject json = new JSONObject(response);
                    String status = json.optString("status");
                    String poruka = json.optString("poruka");

                    SwingUtilities.invokeLater(() -> {
                        if (status.equalsIgnoreCase("OK")) {
                            prikaziPoruku("✅ " + poruka, new Color(0, 128, 0));
                            prikaziSve();
                        } else {
                            prikaziPoruku("❌ " + poruka, Color.RED);
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            prikaziPoruku("⚠️ " + ex.getMessage(), Color.ORANGE));
                }
                return null;
            }
        }.execute();
    }

    private String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
}