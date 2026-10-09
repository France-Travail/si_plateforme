package fr.francetravail.demo.client.api.lpe.api.payload.orientation;

import java.time.LocalDate;

public class Decision {

    private String etatDecision;
    private LocalDate dateDecision;
    private String organismeDecision;
    private Structure structureDecision;
    private String agentDecision;

    private String motifRefus;
    private String commentaireRefus;

    private Structure structure;

    @Override
    public String toString() {
        return "Decision{" +
                "etatDecision='" + etatDecision + '\'' +
                ", dateDecision=" + dateDecision +
                ", organismeDecision='" + organismeDecision + '\'' +
                ", structureDecision=" + structureDecision +
                ", agentDecision='" + agentDecision + '\'' +
                ", structure=" + structure +
                '}';
    }

    public String getEtatDecision() {
        return etatDecision;
    }

    public void setEtatDecision(String etatDecision) {
        this.etatDecision = etatDecision;
    }

    public LocalDate getDateDecision() {
        return dateDecision;
    }

    public void setDateDecision(LocalDate dateDecision) {
        this.dateDecision = dateDecision;
    }

    public String getOrganismeDecision() {
        return organismeDecision;
    }

    public void setOrganismeDecision(String organismeDecision) {
        this.organismeDecision = organismeDecision;
    }

    public Structure getStructureDecision() {
        return structureDecision;
    }

    public void setStructureDecision(Structure structureDecision) {
        this.structureDecision = structureDecision;
    }

    public String getAgentDecision() {
        return agentDecision;
    }

    public void setAgentDecision(String agentDecision) {
        this.agentDecision = agentDecision;
    }

    public String getMotifRefus() {
        return motifRefus;
    }

    public void setMotifRefus(String motifRefus) {
        this.motifRefus = motifRefus;
    }

    public String getCommentaireRefus() {
        return commentaireRefus;
    }

    public void setCommentaireRefus(String commentaireRefus) {
        this.commentaireRefus = commentaireRefus;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}
