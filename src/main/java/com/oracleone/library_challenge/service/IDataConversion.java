package com.oracleone.library_challenge.service;

public interface IDataConversion {
    <T> T getData(String json, Class<T> tClass);
}
