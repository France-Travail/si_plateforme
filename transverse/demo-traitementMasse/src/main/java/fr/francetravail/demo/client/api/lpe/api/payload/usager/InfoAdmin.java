package fr.francetravail.demo.client.api.lpe.api.payload.usager;

import java.util.List;

public class InfoAdmin {

    private String numeroFranceTravail;

    private List<Adresse> adresses;

    private List<Email> emails;

    private EtatCivil etatCivil;

    private List<Telephone> telephones;

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    public String getNumeroFranceTravail() {
        return numeroFranceTravail;
    }

    public void setNumeroFranceTravail(String numeroFranceTravail) {
        this.numeroFranceTravail = numeroFranceTravail;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }

    @Override
    public String toString() {
        return "InfoAdmin{" +
                "numeroFranceTravail='" + numeroFranceTravail + '\'' +
                ", adresses=" + adresses +
                ", emails=" + emails +
                ", etatCivil=" + etatCivil +
                ", telephones=" + telephones +
                '}';
    }

    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }
}
