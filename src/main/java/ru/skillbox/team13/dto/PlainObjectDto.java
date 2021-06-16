package ru.skillbox.team13.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.Map;

public class PlainObjectDto {

    private Map<String, String> properties;

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }
}
