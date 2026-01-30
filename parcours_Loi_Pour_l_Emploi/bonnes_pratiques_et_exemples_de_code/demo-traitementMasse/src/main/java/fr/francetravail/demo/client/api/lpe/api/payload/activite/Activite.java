package fr.francetravail.demo.client.api.lpe.api.payload.activite;

import java.time.Instant;
import java.util.List;

public class Activite {

    private String identifiantActivite;

    private String codeActivite;

    private Usager usager;

    private Instant dateCreation;

    private Instant dateEcheance;

    private Instant dateEcheanceProche;

    private List<AttributSpecifique> attributsSpecifiques;

    @Override
    public String toString() {
        return "Activite{" +
                "identifiantActivite='" + identifiantActivite + '\'' +
                ", codeActivite='" + codeActivite + '\'' +
                ", usager=" + usager +
                ", dateCreation=" + dateCreation +
                ", dateEcheance=" + dateEcheance +
                ", dateEcheanceProche=" + dateEcheanceProche +
                '}';
    }

    public String getIdentifiantActivite() {
        return identifiantActivite;
    }

    public void setIdentifiantActivite(String identifiantActivite) {
        this.identifiantActivite = identifiantActivite;
    }

    public String getCodeActivite() {
        return codeActivite;
    }

    public void setCodeActivite(String codeActivite) {
        this.codeActivite = codeActivite;
    }

    public Usager getUsager() {
        return usager;
    }

    public void setUsager(Usager usager) {
        this.usager = usager;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Instant getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Instant dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public Instant getDateEcheanceProche() {
        return dateEcheanceProche;
    }

    public void setDateEcheanceProche(Instant dateEcheanceProche) {
        this.dateEcheanceProche = dateEcheanceProche;
    }

    public List<AttributSpecifique> getAttributsSpecifiques() {
        return attributsSpecifiques;
    }

    public void setAttributsSpecifiques(List<AttributSpecifique> attributsSpecifiques) {
        this.attributsSpecifiques = attributsSpecifiques;
    }
}
