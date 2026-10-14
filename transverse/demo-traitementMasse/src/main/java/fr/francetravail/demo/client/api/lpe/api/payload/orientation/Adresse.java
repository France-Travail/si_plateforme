package fr.francetravail.demo.client.api.lpe.api.payload.orientation;

public class Adresse {
    private String codeCommune;
    private String libelleCommune;

    private String codePostal;
    private String numeroTypeLibelleVoie;

    @Override
    public String toString() {
        return "Adresse{" +
                "codeCommune='" + codeCommune + '\'' +
                ", libelleCommune='" + libelleCommune + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", numeroTypeLibelleVoie='" + numeroTypeLibelleVoie + '\'' +
                '}';
    }

    /*
     "code_commune": "64478",
             "libelle_commune": "ST FAUST",
             "code_postal": "64110",
             "numero_type_libelle_voie": "40 CHEM DE LAS HIES"
     */

    public String getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public String getLibelleCommune() {
        return libelleCommune;
    }

    public void setLibelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNumeroTypeLibelleVoie() {
        return numeroTypeLibelleVoie;
    }

    public void setNumeroTypeLibelleVoie(String numeroTypeLibelleVoie) {
        this.numeroTypeLibelleVoie = numeroTypeLibelleVoie;
    }
}
