package com.todo.api.utils;

public interface ApiKey<T extends Enum<T>> {
    T getApiKey();
}
