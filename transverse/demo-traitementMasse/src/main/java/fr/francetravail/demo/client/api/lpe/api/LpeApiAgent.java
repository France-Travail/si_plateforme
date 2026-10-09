package fr.francetravail.demo.client.api.lpe.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import fr.francetravail.demo.client.api.lpe.api.payload.activite.Activite;
import fr.francetravail.demo.client.api.lpe.api.payload.orientation.Orientation;
import fr.francetravail.demo.client.api.lpe.api.payload.usager.InfoAdmin;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LpeApiAgent extends ApiAgent{

    private ObjectMapper objectMapperCamelCase;
    public LpeApiAgent() {
        super();
        objectMapperCamelCase = initMapper(PropertyNamingStrategies.LOWER_CAMEL_CASE);
    }

    public String getJetonUsagerParNirEtDateNaissance(String accessToken, String nir, String dateNaissance) throws ParseException, IOException {
        String jsonBody = "{ \"dateNaissance\":\"" + dateNaissance + "\", \"nir\":\"" + nir + "\" }";
        return getJetonUsager(accessToken, jsonBody, "https://api-r.ft-qvr.io/partenaire/rechercher-usager/v2/usagers/par-datenaissance-et-nir");
    }

    public String getJetonUsagerParNumeroFT(String accessToken, String numeroFranceTravail) throws ParseException, IOException {
        String jsonBody = "{\"numeroFranceTravail\":\"" + numeroFranceTravail + "\"}";
        return getJetonUsager(accessToken, jsonBody, "https://api-r.ft-qvr.io/partenaire/rechercher-usager/v2/usagers/par-numero-francetravail");
    }
    private String getJetonUsager(String accessToken, String jsonBody, String url) throws HttpException, ParseException, IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("typeAuth", "/agent");
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("pa-nom-agent", "toto");
        headers.put("pa-prenom-agent", "tutu");
        headers.put("pa-identifiant-agent", "id_toto");

        String jsonResponse = executePostRequest(url, jsonBody, ContentType.APPLICATION_JSON, headers);

        ReponseJetonUsager reponseJetonUsager = objectMapperCamelCase.readValue(jsonResponse, ReponseJetonUsager.class);
        return reponseJetonUsager.getJetonUsager();
    }

    public List<Orientation> getOrientationUsager(String accessToken, String jetonUsager) throws HttpException, ParseException, IOException {
        String url = "https://api-r.ft-qvr.io/partenaire/orientationusager/v1/lectureOrientation";
        Map<String, String> headers = new HashMap<>();
        headers.put("ft-jeton-usager", jetonUsager);
        headers.put("pa-identifiant-agent", "BATCH");
        headers.put("pa-nom-agent", "demoCD56");
        headers.put("pa-prenom-agent", "demoCD56");
        headers.put("Authorization", "Bearer " + accessToken);
        Map<String, String> params = new HashMap<>();
        //params.put("etatOrientation", "OUVERT");

        Optional<String> jsonResponse = executeGetRequest(url, params, headers);
        return objectMapperSnakeCase.readValue(jsonResponse.orElse("[]"), new TypeReference<List<Orientation>>() {
        });
    }

    public List<Activite> getActivitesOperationnellesOrientation(String accessToken) throws HttpException, ParseException, IOException {
        String url = "https://api-r.ft-qvr.io/partenaire/activites-operationnelles/v1/activites/chercher";
        Map<String, String> headers = new HashMap<>();
        headers.put("pa-identifiant-agent", "BATCH");
        headers.put("pa-nom-agent", "demoCD56");
        headers.put("pa-prenom-agent", "demoCD56");
        headers.put("Authorization", "Bearer " + accessToken);
        Map<String, String> params = new HashMap<>();
        params.put("codeActivite", "ACT382");

        Optional<String> jsonResponse = executeGetRequest(url, params, headers);
        return objectMapperCamelCase.readValue(jsonResponse.orElse("[]"), new TypeReference<List<Activite>>() {
        });
    }

    public InfoAdmin getInfoAdmin(String accessToken, String jetonUsager) throws HttpException, ParseException, IOException {
        String url = "https://api-r.ft-qvr.io/partenaire/informations-administratives/v1/usager";
        Map<String, String> headers = new HashMap<>();
        headers.put("ft-jeton-usager", jetonUsager);
        headers.put("pa-identifiant-agent", "BATCH");
        headers.put("pa-nom-agent", "demoCD56");
        headers.put("pa-prenom-agent", "demoCD56");
        headers.put("Authorization", "Bearer " + accessToken);
        Map<String, String> params = new HashMap<>();
        Optional<String> jsonResponse = executeGetRequest(url, params, headers);
        return objectMapperCamelCase.readValue(jsonResponse.orElse("{}"), InfoAdmin.class);
    }

}
