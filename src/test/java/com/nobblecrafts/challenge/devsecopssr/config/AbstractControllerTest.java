package com.nobblecrafts.challenge.devsecopssr.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nobblecrafts.challenge.devsecopssr.config.test.context.DatabaseContext;
import com.nobblecrafts.challenge.devsecopssr.util.ser.JsonUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Import({ContextTestConfiguration.class, DatabaseContextConfig.class})
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected DatabaseContext context;

    protected URI uri(String... path) {
        String joinedPath = "/" + String.join("/", Stream.of(path)
                .map(segment -> segment.replaceAll("^/*|/*$", ""))
                .collect(Collectors.toList()));
        return UriComponentsBuilder.fromUriString(joinedPath).build().toUri();
    }

    protected <DTO> DTO convertJsonToEntity(String json, Class<DTO> clazz) {
        Gson gson = new Gson();
        DTO entity = gson.fromJson(json, clazz);
        return entity;
    }

    protected <DTO> List<DTO> convertJsonToEntityList(String json, Class<DTO> clazz) {
        Gson gson = new Gson();
        List<DTO> entities = gson.fromJson(json, new TypeToken<List<DTO>>() {
        }.getType());
        return entities;
    }

    protected String convertEntityToJson(Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(object);

        JsonElement je = JsonParser.parseString(jsonString);
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;
    }

    protected String prettifyJsonString(String json) {
        return JsonUtils.prettifyJsonString(json);
    }

    protected String getDataFromJson(String json, String... patterns) {
        return JsonUtils.extractFromJson(json, patterns);
    }
}
