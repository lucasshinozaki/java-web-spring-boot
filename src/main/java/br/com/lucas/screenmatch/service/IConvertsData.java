package br.com.lucas.screenmatch.service;

public interface IConvertsData {
    <T> T getData(String json, Class<T> tClass);
}
