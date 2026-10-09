package ft.ftconnect.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpRequest.BodyPublishers;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

public class HttpService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpService.class);

    public HttpService() {

    }

    /*
     * Requête Post
     */
    public String post(String urlpeam, String postdata) {

        LOGGER.info("[HttpService / authcc] urlpeam=" + urlpeam);
        LOGGER.info("[HttpService / authcc] postdata=" + postdata);

        try {

            URL url = new URL(urlpeam);

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(postdata.length()));

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(postdata);

            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));

            String line;
            while ((line = bf.readLine()) != null) {
                LOGGER.info(line);
                return line;
            }

            return null;

        } catch (Exception e) {
            LOGGER.info("Erreur = " + e.getMessage());
            return "Erreur Authentificaton";
        }
    }

    /*
     * Méthode permettant de se connecter en mode client crendential
     * 
     * Requête:
     * curl --location https://url --header 'Content-Type:
     * application/x-www-form-urlencoded
     * --data-urlencode client_id=clientid
     * --data-urlencode client_secret=secret
     * --data-urlencode grant_type=client_credentials
     * --data-urlencode scope=profil_accedant rechercheusager
     * 
     * Réponse:
     * {"access_token":"9d39b4ab-bd02-4319-b2d1-6b7f5b3b2fw<DSSqdsq0d",
     * "scope":"rechercheusager profil_accedant","token_type":"Bearer","expires_in":
     * 1499}
     */
    public String authcc(String urlpeam, String clientid, String clientsecret, String scope) {

        String postData = "client_id=" + clientid
                + "&client_secret=" + clientsecret
                + "&grant_type=client_credentials&scope=" + scope;

        return post(urlpeam, postData);

    }

}
