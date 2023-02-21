package ru.otus.dataprocessor;

//import com.google.gson.Gson;
//import com.google.gson.stream.JsonWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;
    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            mapper.writeValue(fileWriter, data);
        }
    }
}
