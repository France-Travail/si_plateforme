package fr.francetravail.demo.client.api.lpe.dataset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usager {

    @JsonProperty("ReferenceUsager")
    private String referenceUsager;

    @JsonProperty("NIR")
    private String nir;

    @JsonProperty("DateDeNaissance")
    private String dateDeNaissance;

    public String getReferenceUsager() {
        return referenceUsager;
    }

    public void setReferenceUsager(String referenceUsager) {
        this.referenceUsager = referenceUsager;
    }

    public String getNir() {
        return nir;
    }

    public void setNir(String nir) {
        this.nir = nir;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
}
