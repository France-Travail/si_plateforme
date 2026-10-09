package fr.francetravail.demo.client.api.lpe;

import fr.francetravail.demo.client.api.lpe.api.HttpException;
import fr.francetravail.demo.client.api.lpe.api.LpeApiAgent;
import fr.francetravail.demo.client.api.lpe.api.payload.activite.Activite;
import fr.francetravail.demo.client.api.lpe.api.payload.orientation.Orientation;
import fr.francetravail.demo.client.api.lpe.api.payload.usager.InfoAdmin;
import fr.francetravail.demo.client.api.lpe.dataset.DatasetLauncher;
import fr.francetravail.demo.client.api.lpe.dataset.Usager;
import fr.francetravail.demo.client.api.lpe.model.DetailDecisionOrientation;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BenchLoader {
    private static final Logger LOG = LoggerFactory.getLogger(BenchLoader.class);
    private static final int PARALLEL_REQUESTS = 10;
    public static final String USAGER_DATASET_FILE = "resultats_DE_33_400.jsonl";

    private LpeApiAgent apiAgent;

    private String clientId;
    private String clientSecret;

    public BenchLoader(String credentialsFilePath) throws IOException {
        this.apiAgent = new LpeApiAgent();
        this.initCreds(credentialsFilePath);
    }

    private void initCreds(String filePath) throws IOException {
        File file = null;
        if (filePath==null) {
            String tempDir = System.getProperty("java.io.tmpdir");
            file = new File(tempDir, "creds.properties");
            LOG.info("Chargement des credentials depuis {}", file.getAbsoluteFile());
        } else {
            file = new File(filePath);
            LOG.info("Chargement des credentials depuis {}", file.getAbsoluteFile());
        }
        Properties creds = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            creds.load(fis);
        }
        this.clientId = (String) creds.get("clientId");
        this.clientSecret = (String) creds.get("clientSecret");
        if (clientId==null || clientSecret == null) {
            LOG.error("Contenu du fichier {} inadéquat. Propriétés 'clientId' et 'clientSecret' obligatoires", file.getAbsoluteFile());
        }
    }

    private void orientationsEnMasse() throws HttpException, ParseException, IOException, InterruptedException, ExecutionException {
        DatasetLauncher datasetLauncher = new DatasetLauncher();
        List<Usager> usagers = datasetLauncher.loadUsagerDataset(USAGER_DATASET_FILE);
        String accessToken = apiAgent.getAccessToken(clientId, clientSecret);
        long startTime = System.nanoTime();

        Semaphore semaphore = new Semaphore(PARALLEL_REQUESTS);
        List<OrientationApiResponse> results = new ArrayList<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<OrientationApiResponse>> futures = new ArrayList<>();
            for (Usager usager : usagers) {
                futures.add(executor.submit(() -> {
                    semaphore.acquire();
                    try {
                        return getOrientation(accessToken, usager);
                    } finally {
                        semaphore.release();
                    }
                }));
            }
            for (Future<OrientationApiResponse> future : futures) {
                results.add(future.get());
            }
        }
        long endTime = System.nanoTime();
        long totalDurationMs = (endTime - startTime) / 1_000_000;
        traiterResultat(results, totalDurationMs, usagers.size());
    }

    private void traiterResultat(List<OrientationApiResponse> results, long totalDurationMs, int nbUsagers) {
        List<Orientation> orientations =
                results.stream()
                        .map(OrientationApiResponse::orientations)
                        .flatMap(List::stream)
                        .toList();
        LOG.info("Temps total du test : {} ms pour {} usagers et {} orientations ", totalDurationMs, nbUsagers, orientations.size());
        Map<Integer, Long> errorCounts =
                results.stream()
                        .filter(orientationApiResponse -> orientationApiResponse.orientations().isEmpty())
                        .map(OrientationApiResponse::statusCode)
                        .collect(Collectors.groupingBy(
                                Function.identity(),     // clé = le code lui-même
                                Collectors.counting()    // valeur = compteur
                        ));
        // Affichage des erreurs par code HTTP
        traiterErreurs(errorCounts);
        afficheResultatParCommune(orientations);
    }

    private OrientationApiResponse getOrientation(String accessToken, Usager usager) {
        int statusCode = -1;
        List<Orientation> orientations = new ArrayList<>();
        try {
            //String jetonUsager = apiAgent.getJetonUsagerParNirEtDateNaissance(accessToken, usager.getNir(), usager.getDateDeNaissance());
            String jetonUsager = apiAgent.getJetonUsagerParNumeroFT(accessToken, usager.getReferenceUsager());
            orientations.addAll(apiAgent.getOrientationUsager(accessToken, jetonUsager));
        } catch (HttpException e) {
            LOG.error(e.getMessage());
            statusCode = e.getStatusCode();
        } catch (Exception e) {
            LOG.error("Erreur : {}", e.getMessage());
            statusCode = 0;
        }
        return new OrientationApiResponse(orientations, statusCode);
    }

    private void activitesOrientationsEnMasse() throws HttpException, ParseException, IOException, InterruptedException, ExecutionException {
        String accessToken = apiAgent.getAccessToken(clientId, clientSecret);

        long startTime = System.nanoTime();
        List<Activite> activites = apiAgent.getActivitesOperationnellesOrientation(accessToken);
        long endTime = System.nanoTime();
        long totalDurationMs = (endTime - startTime) / 1_000_000;
        LOG.info("Temps total de récupération des décisions d'orientation : {} ms pour {} décisions d'orientation", totalDurationMs, activites.size());

        Semaphore semaphore = new Semaphore(PARALLEL_REQUESTS);
        List<DetailOrientationApiResponse> results = new ArrayList<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<DetailOrientationApiResponse>> futures = new ArrayList<>();
            for (Activite activite : activites) {
                futures.add(executor.submit(() -> {
                    semaphore.acquire();
                    try {
                        return getDetailDecisionOrientation(activite, accessToken);
                    } finally {
                        semaphore.release();
                    }
                }));
            }
            for (Future<DetailOrientationApiResponse> future : futures) {
                results.add(future.get());
            }
        }
        endTime = System.nanoTime();
        totalDurationMs = (endTime - startTime) / 1_000_000;
        traiterResultat(results, totalDurationMs);
    }

    private DetailOrientationApiResponse getDetailDecisionOrientation(Activite activite, String accessToken) {
        String idFT = "";
        if (activite.getUsager() != null) {
            idFT = activite.getUsager().getIdentifiant();
        }
        String jetonUsager = "";
        DetailDecisionOrientation ddo = null;
        int statusCode = -1;
        try {
            jetonUsager = apiAgent.getJetonUsagerParNumeroFT(accessToken, idFT);
            List<Orientation> orientations = apiAgent.getOrientationUsager(accessToken, jetonUsager);
            InfoAdmin infoAdmin = apiAgent.getInfoAdmin(accessToken, jetonUsager);
            ddo = new DetailDecisionOrientation(orientations, infoAdmin);
        } catch (HttpException e) {
            LOG.error("{} ...  id FT : {} ... jetonUsager : {} : ", e.getMessage(), idFT, jetonUsager);
            statusCode = e.getStatusCode();
        } catch (Exception e) {
            LOG.error("Erreur : {}", e.getMessage());
            statusCode = 0; // 0 = erreur interne
        }
        return new DetailOrientationApiResponse(ddo, statusCode);
    }

    private void traiterResultat(List<DetailOrientationApiResponse> results, long totalDurationMs) {
        List<DetailDecisionOrientation> ddos =
                results.stream()
                        .map(DetailOrientationApiResponse::detailDecisionOrientation)
                        .filter(Objects::nonNull)
                        .toList();
        LOG.info("Temps total avec récupération du détail des orientations et info usager: {} ms pour {} decisions", totalDurationMs, ddos.size());

        Map<Integer, Long> errorCounts =
                results.stream()
                        .filter(detailOrientationApiResponse -> detailOrientationApiResponse.detailDecisionOrientation() == null)
                        .map(DetailOrientationApiResponse::statusCode)
                        .collect(Collectors.groupingBy(
                                Function.identity(),     // clé = le code lui-même
                                Collectors.counting()    // valeur = compteur
                        ));

        // Affichage des erreurs par code HTTP
        traiterErreurs(errorCounts);
        afficheDecisionDetailParCommune(ddos);
    }

    private void traiterErreurs(Map<Integer, Long> errorCounts) {
        LOG.warn("Résumé des erreurs HTTP : {}", ((errorCounts.isEmpty())?"pas d'erreur":""));
        for (Map.Entry<Integer, Long> entry : errorCounts.entrySet()) {
            LOG.warn("Code HTTP {} : {} erreurs", entry.getKey(), entry.getValue());
        }
        LOG.info("Nombre total de retries sur 429 : {}", apiAgent.getTotalRetries());
    }

    private void afficheResultatParCommune(List<Orientation> orientations){
        LOG.info("Répartition orientations / code commune : ");
        Map<String, List<Orientation>> orientationsParCodeCommune = orientations.stream()
                .collect(Collectors.groupingBy(o -> o.getCriteresOrientation().getAdresse().getCodeCommune()));
        orientationsParCodeCommune.forEach((code, items) -> {
            LOG.info("{} -> ", code);
            items.forEach(item -> LOG.info("  {}", item));
            LOG.info("\n");
        });
    }

    private void afficheActivitesParCommune(List<Activite> activites){
        LOG.info("Répartition orientations / code commune : ");
        Map<String, List<Activite>> activitesParCodeCommune = activites.stream()
                .flatMap(a -> a.getAttributsSpecifiques().stream()
                        .filter(b -> "codeInseeCommune".equals(b.getNom()))
                        .map(b -> new SimpleEntry<String, Activite>(b.getValeur(), a))
                )
                .collect(Collectors.groupingBy( entry -> String.valueOf(entry.getKey()),
                        Collectors.mapping(SimpleEntry::getValue, Collectors.toList())));

        activitesParCodeCommune.forEach((code, items) -> {
            LOG.info("{} -> ", code);
            items.forEach(item -> LOG.info("  {}", item));
        });
    }

    private void afficheDecisionDetailParCommune(List<DetailDecisionOrientation> details){
        LOG.info("Répartition orientations / code commune : ");
        Map<String, List<DetailDecisionOrientation>> detailsParCodeCommune = details.stream()
                .flatMap(a -> a.getInfoAdmin().getAdresses().stream()
                        .map(b -> new SimpleEntry<String, DetailDecisionOrientation>(b.getCodeInseeCommune(), a))
                )
                .collect(Collectors.groupingBy( entry -> String.valueOf(entry.getKey()),
                        Collectors.mapping(SimpleEntry::getValue, Collectors.toList())));

        detailsParCodeCommune.forEach((code, items) -> {
            LOG.info("{} -> ", code);
            items.forEach(item -> LOG.info("  {}", item));
        });
    }

    public static void main(String[] args) throws IOException, HttpException, ParseException, InterruptedException, ExecutionException {
        String filePath = args.length >= 2?args[1]:null;
        BenchLoader loader = new BenchLoader(filePath);
        String typeApi = args[0];
        if (typeApi != null) {
            switch (typeApi) {
                case "orientation" :
                    loader.orientationsEnMasse();
                    break;
                case "activite-operationnelle" :
                    loader.activitesOrientationsEnMasse();
                    break;
                default:
                    throw new UnsupportedOperationException("type api inconnu : "+ args[0]);
            }
        }
    }
}

record OrientationApiResponse(List<Orientation> orientations, int statusCode){}
record DetailOrientationApiResponse(DetailDecisionOrientation detailDecisionOrientation, int statusCode){}