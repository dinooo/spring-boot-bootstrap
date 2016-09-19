package com.piksel.movies.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class ObjectMapperFactory {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper()
                .registerModule(new Hibernate4Module());
    }

    public static ObjectMapper create() {
        return objectMapper;
    }

    static {
        objectMapper = new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .failOnUnknownProperties(false)
                .indentOutput(true)
                .modulesToInstall(new JodaModule())
                .modulesToInstall(new Hibernate4Module(){{ disable(Feature.USE_TRANSIENT_ANNOTATION); }})
                .failOnUnknownProperties(false).build();
    }



}