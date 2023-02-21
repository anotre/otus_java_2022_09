package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ResourcesFileLoader implements Loader {

    private final String filename;

    public ResourcesFileLoader(String fileName) {
        this.filename = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        List<Measurement> measurements;
        InputStream inputStream = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(filename);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        mapper.registerModule(module);

        if (inputStream == null) {
            throw new RuntimeException("Resource cannot be found");
        }
        try (inputStream) {
            measurements = mapper.readValue(inputStream, new TypeReference<>(){});
        }

        return measurements;
    }
}
