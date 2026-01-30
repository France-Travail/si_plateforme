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
     * post
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
     * -----------------------------------------------------------------------------
     * Cette méthode permet d'éffectuer la validation du code retour du flow
     * Validation du code de retour se fait à la receprion du flow retour
     * http://localhost:8080/auth/auth.html?code=XXXXXX&scope=XXXX&&iss=XXXX&
     * client_id=XXXX
     *
     * Appel à effectué pour valider le code retour:
     * curl --location url --header 'Content-Type: application/x-www-form-urlencoded
     * --data-urlencode client_id=XXXXXXX
     * --data-urlencode client_secret=XXXXXXXXX
     * --data-urlencode grant_type=authorization_code
     * --data-urlencode code=XXXXXXXXX
     * --data-urlencode redirect_uri==http://localhost:8080/auth/auth.html
     *
     * Résultat de la Requête
     * {"access_token":"XXXXXXX","refresh_token":"XXXXXXXX","scope":"openid",
     * "id_token":"XXXXXXXXX","token_type":"Bearer","expires_in":1499}
     *
     */
    public String authagentcode(String urlpeam, String clientid, String clientsecret, String scope, String code) {

        String postData = "client_id=" + clientid
                + "&client_secret=" + clientsecret
                + "&grant_type=authorization_code&code=" + code;
        return post(urlpeam, postData);
    }

}
