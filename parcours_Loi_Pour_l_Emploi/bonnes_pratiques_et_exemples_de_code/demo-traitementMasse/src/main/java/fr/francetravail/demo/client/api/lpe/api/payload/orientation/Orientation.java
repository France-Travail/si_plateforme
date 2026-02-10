package fr.francetravail.demo.client.api.lpe.api.payload.orientation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.francetravail.demo.client.api.lpe.api.InstantWithoutZDeserializer;

import java.time.Instant;
import java.time.LocalDate;

public class Orientation {

    private String parcours;
    private String organisme;

    @JsonProperty("organisme_decision")
    private String organismeDecision;

    private Structure structure;

    private Structure structureDecision;
    private String statut;

    private String etat;
    private String agentCreation;

    private String agentDerniereModification;
    private LocalDate dateEntreeParcours;
    private LocalDate dateSortieParcours;

    private String motifSortieParcours;
    private String origine;

    private Decision decision;

    @JsonDeserialize(using = InstantWithoutZDeserializer.class)
    private Instant dateCreation;
    @JsonDeserialize(using = InstantWithoutZDeserializer.class)
    private Instant dateModification;

    private CriteresOrientation criteresOrientation;

    @Override
    public String toString() {
        return "{" +
                "parcours='" + parcours + '\'' +
                ", organisme='" + organisme + '\'' +
                ", etat='" + etat + '\'' +
                ", origine='" + origine + '\'' +
                ", criteresOrientation=" + criteresOrientation +
                ", decision=" + decision +
                '}';
    }

    public String getParcours() {
        return parcours;
    }

    public void setParcours(String parcours) {
        this.parcours = parcours;
    }

    public String getOrganisme() {
        return organisme;
    }

    public void setOrganisme(String organisme) {
        this.organisme = organisme;
    }

    public String getOrganismeDecision() {
        return organismeDecision;
    }

    public void setOrganismeDecision(String organismeDecision) {
        this.organismeDecision = organismeDecision;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Structure getStructureDecision() {
        return structureDecision;
    }

    public void setStructureDecision(Structure structureDecision) {
        this.structureDecision = structureDecision;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        statut = statut;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Instant getDateModification() {
        return dateModification;
    }

    public void setDateModification(Instant dateModification) {
        this.dateModification = dateModification;
    }

    public CriteresOrientation getCriteresOrientation() {
        return criteresOrientation;
    }

    public void setCriteresCrientation(CriteresOrientation criteresCrientation) {
        this.criteresOrientation = criteresCrientation;
    }

    public String getAgentCreation() {
        return agentCreation;
    }

    public void setAgentCreation(String agentCreation) {
        this.agentCreation = agentCreation;
    }

    public void setCriteresOrientation(CriteresOrientation criteresOrientation) {
        this.criteresOrientation = criteresOrientation;
    }

    public String getAgentDerniereModification() {
        return agentDerniereModification;
    }

    public void setAgentDerniereModification(String agentDerniereModification) {
        this.agentDerniereModification = agentDerniereModification;
    }

    public LocalDate getDateEntreeParcours() {
        return dateEntreeParcours;
    }

    public void setDateEntreeParcours(LocalDate dateEntreeParcours) {
        this.dateEntreeParcours = dateEntreeParcours;
    }

    public LocalDate getDateSortieParcours() {
        return dateSortieParcours;
    }

    public void setDateSortieParcours(LocalDate dateSortieParcours) {
        this.dateSortieParcours = dateSortieParcours;
    }

    public String getMotifSortieParcours() {
        return motifSortieParcours;
    }

    public void setMotifSortieParcours(String motifSortieParcours) {
        this.motifSortieParcours = motifSortieParcours;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}
