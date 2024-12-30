package com.lfch.challenge_literatura_alura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConvertirDatos implements IConvertirDatos{
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T obtenerListaDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json.toString(),clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
