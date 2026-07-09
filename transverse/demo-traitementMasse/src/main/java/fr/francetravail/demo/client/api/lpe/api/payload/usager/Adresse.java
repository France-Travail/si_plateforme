package fr.francetravail.demo.client.api.lpe.api.payload.usager;

public class Adresse {

    private String codeInseeCommune;

    private String codePostal;

    private String complementAdresse;

    private String complementDestinataire;

    private String complementDistribution;

    private String libelleCommune;

    private String libellePays;

    private String numeroTypeLibelleVoie;

    private Email emails;

    private String indicateurResidentQPV;

    public String getCodeInseeCommune() {
        return codeInseeCommune;
    }

    public void setCodeInseeCommune(String codeInseeCommune) {
        this.codeInseeCommune = codeInseeCommune;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getComplementAdresse() {
        return complementAdresse;
    }

    public void setComplementAdresse(String complementAdresse) {
        this.complementAdresse = complementAdresse;
    }

    public String getComplementDestinataire() {
        return complementDestinataire;
    }

    public void setComplementDestinataire(String complementDestinataire) {
        this.complementDestinataire = complementDestinataire;
    }

    public String getComplementDistribution() {
        return complementDistribution;
    }

    public void setComplementDistribution(String complementDistribution) {
        this.complementDistribution = complementDistribution;
    }

    public String getLibelleCommune() {
        return libelleCommune;
    }

    public void setLibelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
    }

    public String getLibellePays() {
        return libellePays;
    }

    public void setLibellePays(String libellePays) {
        this.libellePays = libellePays;
    }

    public String getNumeroTypeLibelleVoie() {
        return numeroTypeLibelleVoie;
    }

    public void setNumeroTypeLibelleVoie(String numeroTypeLibelleVoie) {
        this.numeroTypeLibelleVoie = numeroTypeLibelleVoie;
    }

    public Email getEmails() {
        return emails;
    }

    public void setEmails(Email emails) {
        this.emails = emails;
    }

    public String getIndicateurResidentQPV() {
        return indicateurResidentQPV;
    }

    public void setIndicateurResidentQPV(String indicateurResidentQPV) {
        this.indicateurResidentQPV = indicateurResidentQPV;
    }
}
