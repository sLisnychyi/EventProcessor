package com.slisnychyi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slisnychyi.model.Event;
import java.util.List;

public class TestUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<Event> readEvents(String value) {
        try {
            return OBJECT_MAPPER.readValue(value, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
