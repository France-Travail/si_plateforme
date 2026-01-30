package fr.francetravail.demo.client.api.lpe.api.payload.orientation;

public class Structure {

    private String codeAurore;
    private String codeSafir;
    private String libelleStructure;

    /*"code_aurore": "NAQ0367",
            "code_safir": "64008",
            "libelle_structure":

     */

    public String getCodeAurore() {
        return codeAurore;
    }

    public void setCodeAurore(String codeAurore) {
        this.codeAurore = codeAurore;
    }

    public String getCodeSafir() {
        return codeSafir;
    }

    public void setCodeSafir(String codeSafir) {
        this.codeSafir = codeSafir;
    }

    public String getLibelleStructure() {
        return libelleStructure;
    }

    public void setLibelleStructure(String libelleStructure) {
        this.libelleStructure = libelleStructure;
    }
}