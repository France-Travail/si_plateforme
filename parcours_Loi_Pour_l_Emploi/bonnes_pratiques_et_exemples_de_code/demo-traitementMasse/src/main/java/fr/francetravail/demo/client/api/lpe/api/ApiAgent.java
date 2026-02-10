package fr.francetravail.demo.client.api.lpe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class ApiAgent {
    private static final Logger LOG = LoggerFactory.getLogger(ApiAgent.class);
    private static final String URL_ACCESS_TOKEN = "https://proxyproconnect-r.ft-qvr.net/connexion/oauth2/access_token?realm=%2Fagent";
    protected ObjectMapper objectMapperSnakeCase;
    private Retry retry;

    private final AtomicInteger totalRetries = new AtomicInteger(0);
    protected ApiAgent() {
        retry = initRetryPolicy();
        objectMapperSnakeCase = initMapper(PropertyNamingStrategies.SNAKE_CASE);
    }

    protected String executePostRequest(String url, String body, ContentType bodyContentType, Map<String, String> headers) throws ParseException, HttpException, IOException {
        HttpResponse response = null;
        Request request = Request
                .post(url)
                .bodyString(body, bodyContentType);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.setHeader(entry.getKey(), entry.getValue());
        }
        response = executeWithRetry(request, url);

        return EntityUtils.toString(((ClassicHttpResponse)response).getEntity());
    }

    private static void handleError(String url, int statusCode, String responseBody) {
        if (statusCode == 429) {
            throw new TooManyRequestsException(statusCode, url, responseBody);
        }
        if (statusCode >= 400) {
            throw new HttpException(statusCode, url, responseBody);
        }
    }

    protected Optional<String> executeGetRequest(String url, Map<String, String> requestParams, Map<String, String> headers) throws ParseException {
        HttpResponse response = null;
        try {
            String urlWithParams = url;
            if (requestParams != null && !requestParams.isEmpty()) {
                urlWithParams += "?" + requestParams.entrySet().stream()
                        .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                        .collect(Collectors.joining("&"));
            }
            Request request = Request
                    .get(urlWithParams)
                    .connectTimeout(Timeout.ofSeconds(180))
                    .responseTimeout(Timeout.ofSeconds(180));

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }

            response = executeWithRetry(request, url);
            return Optional.of(EntityUtils.toString(((ClassicHttpResponse)response).getEntity()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse executeWithRetry(Request request, String url) {
        Supplier<HttpResponse> requestSupplier = Retry.decorateSupplier(retry, () -> {
            try {
                return executeHttpRequest(request, url);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
        return requestSupplier.get();
    }

    private HttpResponse executeHttpRequest(Request request, String url) throws IOException, ParseException {
        ClassicHttpResponse response = (ClassicHttpResponse) request.execute().returnResponse();
        String responseBody = EntityUtils.toString(response.getEntity());
        handleError(url, response.getCode(), responseBody);
        return response;
    }

    private Retry initRetryPolicy(){
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(5000))
                .retryExceptions(TooManyRequestsException.class)
                .failAfterMaxAttempts(true)
                .build();
        RetryRegistry registry = RetryRegistry.of(config);
        registry.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    Retry addedRetry = entryAddedEvent.getAddedEntry();
                    LOG.info("Retry {} added", addedRetry.getName());
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    Retry removedRetry = entryRemovedEvent.getRemovedEntry();
                    LOG.info("Retry {} removed", removedRetry.getName());
                });
        Retry tooManyRequestsRetry = registry.retry("tooManyRequests");

        tooManyRequestsRetry.getEventPublisher()
                .onRetry(event -> {
                    totalRetries.incrementAndGet();
                    LOG.info("Retry n°{} sur {}", event.getNumberOfRetryAttempts(), event.getName());
                })
                .onError(event -> LOG.error("Échec après {} retries pour la requête : {}", event.getNumberOfRetryAttempts(), event.getName(), event.getLastThrowable()));

        return tooManyRequestsRetry;
    }

    public String getAccessToken(String clientId, String clientSecret) throws HttpException, ParseException, IOException {
        String grantType = "client_credentials";
        String scope = "api_rechercher-usagerv2 rechercheusager profil_accedant orientationusager api_orientationusagerv1 api_activites-operationnellesv1 api_activites-operationnellesv1 activitesCD referentielCD api_informations-administrativesv1 informationsadministrativesusager api_petshopv1 personnes";
        String url = URL_ACCESS_TOKEN;
        String formBody = "client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8)
                + "&grant_type=" + URLEncoder.encode(grantType, StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8);

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        String jsonResponse = executePostRequest(url, formBody, ContentType.APPLICATION_FORM_URLENCODED, headers);

        ReponseAccessToken reponseAccessToken = objectMapperSnakeCase.readValue(jsonResponse, ReponseAccessToken.class);
        return reponseAccessToken.getAccessToken();
    }

    protected ObjectMapper initMapper(PropertyNamingStrategy propertyNamingStrategy) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        return objectMapper;
    }

    public AtomicInteger getTotalRetries() {
        return totalRetries;
    }
}
