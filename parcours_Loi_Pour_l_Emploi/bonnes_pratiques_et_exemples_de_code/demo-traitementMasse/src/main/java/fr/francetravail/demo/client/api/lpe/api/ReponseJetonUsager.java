package fr.francetravail.demo.client.api.lpe.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReponseJetonUsager {

    private String jetonUsager;

    public String getJetonUsager() {
        return jetonUsager;
    }

    public void setJetonUsager(String jetonUsager) {
        this.jetonUsager = jetonUsager;
    }
}
