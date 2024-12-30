package com.lfch.challenge_literatura_alura.service;


public interface IConvertirDatos {
    <T> T obtenerListaDatos(String json, Class<T> clase);
}