package fr.francetravail.demo.client.api.lpe.api.payload.usager;

import java.time.LocalDate;

public class EtatCivil {

    private String civilite;

    private LocalDate dateNaissance;

    private String nir;

    private String nom;

    private String nomCorrespondance;

    private String prenom;

    private String prenomCorrespondance;

    @Override
    public String toString() {
        return "EtatCivil{" +
                "civilite='" + civilite + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", nir='" + nir + '\'' +
                ", nom='" + nom + '\'' +
                ", nomCorrespondance='" + nomCorrespondance + '\'' +
                ", prenom='" + prenom + '\'' +
                ", prenomCorrespondance='" + prenomCorrespondance + '\'' +
                '}';
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNir() {
        return nir;
    }

    public void setNir(String nir) {
        this.nir = nir;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCorrespondance() {
        return nomCorrespondance;
    }

    public void setNomCorrespondance(String nomCorrespondance) {
        this.nomCorrespondance = nomCorrespondance;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenomCorrespondance() {
        return prenomCorrespondance;
    }

    public void setPrenomCorrespondance(String prenomCorrespondance) {
        this.prenomCorrespondance = prenomCorrespondance;
    }

}
