package fr.francetravail.demo.client.api.lpe.api.payload.usager;

public class Email {

    private String adresseEmail;

    @Override
    public String toString() {
        return "Email{" +
                "adresseEmail='" + adresseEmail + '\'' +
                '}';
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }
}
