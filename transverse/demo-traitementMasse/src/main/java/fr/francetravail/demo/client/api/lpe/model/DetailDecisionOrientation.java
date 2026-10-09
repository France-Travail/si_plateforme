package fr.francetravail.demo.client.api.lpe.model;

import fr.francetravail.demo.client.api.lpe.api.payload.orientation.Orientation;
import fr.francetravail.demo.client.api.lpe.api.payload.usager.InfoAdmin;

import java.util.List;

public class DetailDecisionOrientation {

    private List<Orientation> orientations;

    private InfoAdmin infoAdmin;

    public DetailDecisionOrientation(List<Orientation> orientations, InfoAdmin infoAdmin) {
        this.orientations = orientations;
        this.infoAdmin = infoAdmin;
    }

    @Override
    public String toString() {
        return "DetailDecisionOrientation{" +
                "orientations=" + orientations +
                ", infoAdmin=" + infoAdmin +
                '}';
    }

    public List<Orientation> getOrientations() {
        return orientations;
    }

    public InfoAdmin getInfoAdmin() {
        return infoAdmin;
    }
}
