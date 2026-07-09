package fr.francetravail.demo.client.api.lpe.dataset;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class DatasetLauncher {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public List<Usager> loadUsagerDataset(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            MappingIterator<Usager> iterator = objectMapper.readerFor(Usager.class).readValues(inputStream);
            return iterator.readAll(); // Charge tout en mémoire
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement du fichier " + fileName, e);
        }
    }

}
