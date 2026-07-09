package fr.francetravail.demo.client.api.lpe.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class InstantWithoutZDeserializer extends JsonDeserializer<Instant> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            .withZone(ZoneOffset.UTC); // UTC par défaut, mais sans `Z`

    @Override
    public Instant deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String value = parser.getText();
        TemporalAccessor temporal = formatter.parse(value);
        return Instant.from(temporal); // Convertir en Instant
    }
}
