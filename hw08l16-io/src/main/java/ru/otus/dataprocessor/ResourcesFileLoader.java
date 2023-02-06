package ru.otus.dataprocessor;

import ru.otus.model.Measurement;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ResourcesFileLoader implements Loader {

    private final String filename;

    public ResourcesFileLoader(String fileName) {
        this.filename = fileName;
    }

    @Override
    public List<Measurement> load() {
        InputStream inputStream = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new RuntimeException("Resource cannot be found");
        }

        try (inputStream; JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream))) {
            return Arrays.asList(new Gson().fromJson(jsonReader, Measurement[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
