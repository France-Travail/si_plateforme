package fr.francetravail.demo.client.api.lpe.api.payload.activite;

public class Usager {

    private String identifiant;

    private String typeIdentifiant;

    @Override
    public String toString() {
        return "Usager{" +
                "identifiant='" + identifiant + '\'' +
                ", typeIdentifiant='" + typeIdentifiant + '\'' +
                '}';
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getTypeIdentifiant() {
        return typeIdentifiant;
    }

    public void setTypeIdentifiant(String typeIdentifiant) {
        this.typeIdentifiant = typeIdentifiant;
    }
}
