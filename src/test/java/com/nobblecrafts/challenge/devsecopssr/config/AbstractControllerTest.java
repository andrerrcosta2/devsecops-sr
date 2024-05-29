package com.nobblecrafts.challenge.devsecopssr.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nobblecrafts.challenge.devsecopssr.config.test.context.DatabaseContext;
import com.nobblecrafts.challenge.devsecopssr.security.filter.JwtCookieFilter;
import com.nobblecrafts.challenge.devsecopssr.security.filter.SessionExceptionCookieFilter;
import com.nobblecrafts.challenge.devsecopssr.util.ser.JsonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Import({CustomTestConfiguration.class, DatabaseContextConfig.class})
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected DatabaseContext context;
    @Autowired
    private WebApplicationContext wac;
    @MockBean
    private JwtCookieFilter jwtCookieFilter;
    @MockBean
    private SessionExceptionCookieFilter sessionExceptionCookieFilter;

    @BeforeEach
    void setup() throws ServletException, IOException {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(jwtCookieFilter, sessionExceptionCookieFilter)  // Add the mocked filters here
                .build();

        Mockito.doAnswer(invocation -> {
                    HttpServletRequest request = invocation.getArgument(0);
                    HttpServletResponse response = invocation.getArgument(1);
                    FilterChain filterChain = invocation.getArgument(2);
                    filterChain.doFilter(request, response);
                    var auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth == null) {
                        System.out.println("\n\nNo Authorization at all:\n\n");
                    } else {
                        System.out.println("\n\nCurrent authentication for: " +
                                auth.getName()
                                + "\n\n");
                    }
                    return null;
                }).when(jwtCookieFilter)
                .doFilter(any(HttpServletRequest.class),
                        any(HttpServletResponse.class),
                        any(FilterChain.class));

        Mockito.doAnswer(invocation -> {
                    HttpServletRequest request = invocation.getArgument(0);
                    HttpServletResponse response = invocation.getArgument(1);
                    FilterChain filterChain = invocation.getArgument(2);
                    filterChain.doFilter(request, response);
                    return null;
                }).when(sessionExceptionCookieFilter)
                .doFilter(any(HttpServletRequest.class),
                        any(HttpServletResponse.class),
                        any(FilterChain.class));
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
