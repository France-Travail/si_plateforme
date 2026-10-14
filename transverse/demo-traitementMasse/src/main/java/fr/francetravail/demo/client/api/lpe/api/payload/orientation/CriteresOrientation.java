package fr.francetravail.demo.client.api.lpe.api.payload.orientation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.francetravail.demo.client.api.lpe.api.InstantWithoutZDeserializer;

import java.time.Instant;
import java.time.LocalDate;

public class CriteresOrientation {

    private String situationProfessionnelle;
    private String niveauEtude;

    private String typeEmploi;

    @JsonProperty("capacite_a_travailler")
    private String capaciteATravailler;

    private String projetPro;
    private String contrainteSante;
    private String contrainteLogement;
    private String contrainteMobilite;
    private String contrainteFamiliale;
    private String contrainteFinanciere;

    private String contrainteNumerique;

    private String contrainteAdministrativeJuridique;
    private String contrainteFrancaisCalcul;
    private boolean brsa;
    private boolean boe;
    private boolean baeeh;
    private boolean scolariteEtablissementSpecialise;
    private boolean esat;
    private boolean boe_souhait_accompagnement;
    private LocalDate date_naissance;

    private Adresse adresse;

    @Override
    public String toString() {
        return "{" +
                "situationProfessionnelle='" + situationProfessionnelle + '\'' +
                ", brsa=" + brsa +
                '}';
    }

    @JsonProperty("date_saisie_donnees")
    private Instant dateSaisieDonnees;

    @JsonDeserialize(using = InstantWithoutZDeserializer.class)
    public Instant getDateSaisieDonnees() {
        return dateSaisieDonnees;
    }

    public void setDateSaisieDonnees(Instant dateSaisieDonnees) {
        this.dateSaisieDonnees = dateSaisieDonnees;
    }
    /*"situation_professionnelle": "JAMAIS",
            "niveau_etude": "AFS",
            "capacite_a_travailler": true,
            "projet_pro": "SALARIAT",
            "contrainte_sante": "INCAPACITE_RECHERCHE_ACTIVITE_PRO",
            "contrainte_logement": "AUCUNE",
            "contrainte_mobilite": "AUCUNE",
            "contrainte_familiale": "AUCUNE",
            "contrainte_financiere": "AUCUNE",
            "contrainte_numerique": "AUCUNE",
            "contrainte_administrative_juridique": "AUCUNE",
            "contrainte_francais_calcul": "AUCUNE",
            "brsa": true,
            "boe": false,
            "baeeh": false,
            "scolarite_etablissement_specialise": false,
            "esat": false,
            "boe_souhait_accompagnement": false,
            "date_naissance": "1960-02-09",
            "adresse": {
        "code_commune": "64478",
                "libelle_commune": "ST FAUST",
                "code_postal": "64110",
                "numero_type_libelle_voie": "40 CHEM DE LAS HIES"
    },
    */

    public String getSituationProfessionnelle() {
        return situationProfessionnelle;
    }

    public void setSituationProfessionnelle(String situationProfessionnelle) {
        this.situationProfessionnelle = situationProfessionnelle;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public String getCapaciteATravailler() {
        return capaciteATravailler;
    }

    public void setCapaciteATravailler(String capaciteATravailler) {
        this.capaciteATravailler = capaciteATravailler;
    }

    public String getProjetPro() {
        return projetPro;
    }

    public void setProjetPro(String projetPro) {
        this.projetPro = projetPro;
    }

    public String getContrainteSante() {
        return contrainteSante;
    }

    public void setContrainteSante(String contrainteSante) {
        this.contrainteSante = contrainteSante;
    }

    public String getContrainteLogement() {
        return contrainteLogement;
    }

    public void setContrainteLogement(String contrainteLogement) {
        this.contrainteLogement = contrainteLogement;
    }

    public String getContrainteMobilite() {
        return contrainteMobilite;
    }

    public void setContrainteMobilite(String contrainteMobilite) {
        this.contrainteMobilite = contrainteMobilite;
    }

    public String getContrainteFamiliale() {
        return contrainteFamiliale;
    }

    public void setContrainteFamiliale(String contrainteFamiliale) {
        this.contrainteFamiliale = contrainteFamiliale;
    }

    public String getContrainteFinanciere() {
        return contrainteFinanciere;
    }

    public void setContrainteFinanciere(String contrainteFinanciere) {
        this.contrainteFinanciere = contrainteFinanciere;
    }

    public String getContrainteNumerique() {
        return contrainteNumerique;
    }

    public void setContrainteNumerique(String contrainteNumerique) {
        this.contrainteNumerique = contrainteNumerique;
    }

    public String getContrainteAdministrativeJuridique() {
        return contrainteAdministrativeJuridique;
    }

    public void setContrainteAdministrativeJuridique(String contrainteAdministrativeJuridique) {
        this.contrainteAdministrativeJuridique = contrainteAdministrativeJuridique;
    }

    public String getContrainteFrancaisCalcul() {
        return contrainteFrancaisCalcul;
    }

    public void setContrainteFrancaisCalcul(String contrainteFrancaisCalcul) {
        this.contrainteFrancaisCalcul = contrainteFrancaisCalcul;
    }

    public boolean isBrsa() {
        return brsa;
    }

    public void setBrsa(boolean brsa) {
        this.brsa = brsa;
    }

    public boolean isBoe() {
        return boe;
    }

    public void setBoe(boolean boe) {
        this.boe = boe;
    }

    public boolean isBaeeh() {
        return baeeh;
    }

    public void setBaeeh(boolean baeeh) {
        this.baeeh = baeeh;
    }

    public boolean isScolariteEtablissementSpecialise() {
        return scolariteEtablissementSpecialise;
    }

    public void setScolariteEtablissementSpecialise(boolean scolariteEtablissementSpecialise) {
        this.scolariteEtablissementSpecialise = scolariteEtablissementSpecialise;
    }

    public boolean isEsat() {
        return esat;
    }

    public void setEsat(boolean esat) {
        this.esat = esat;
    }

    public boolean isBoe_souhait_accompagnement() {
        return boe_souhait_accompagnement;
    }

    public void setBoe_souhait_accompagnement(boolean boe_souhait_accompagnement) {
        this.boe_souhait_accompagnement = boe_souhait_accompagnement;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getTypeEmploi() {
        return typeEmploi;
    }

    public void setTypeEmploi(String typeEmploi) {
        this.typeEmploi = typeEmploi;
    }
}
