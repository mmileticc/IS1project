/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author Miletic
 */
public enum TipPoruke {
    KREIRAJ_MESTO,
    KREIRAJ_KORISNIKA,
    PROMENI_EMAIL,
    PROMENI_MESTO,
    KREIRAJ_AUDIO_SNIMAK,
    PROMENI_IME_SNIMKA,
    DODAJ_KATEGORIJU_SNIMKU,
    OBRISI_AUDIO_SNIMAK,
    KREIRAJ_KATEGORIJU,
    KREIRAJ_PAKET,
    PROMENI_CENU_PAKETA,
    KREIRAJ_PRETPLATU,
    KREIRAJ_SLUSANJE,
    DODAJ_OMILJENI,
    KREIRAJ_OCENU,
    IZMENA_OCENE,
    OBRISI_OCENU,
    DOHVATI_SVA_MESTA,
    DOHVATI_SVE_KORISNIKE,
    DOHVATI_SVE_KATEGORIJE,
    DOHVATI_SVE_AUDIO_SNIMKE,
    DOHVATI_KATEGORIJE_AUDIA,
    DOHVATI_SVE_PAKETE,
    DOHVATI_PRETPLATE_KORISNIKA,
    DOHVATI_SLUSANJA_AUDIA,
    DOHVATI_OCENE_AUDIA,
    DOHVATI_OMILJENE_AUDIJE;

    @Override
    public String toString() {
        return this.name(); // npr. "kreirajMesto"

    }
}
