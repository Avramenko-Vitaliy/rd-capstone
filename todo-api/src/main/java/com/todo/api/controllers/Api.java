package com.todo.api.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Api {

    public static final String ROOT_PATH = "/api";

    @UtilityClass
    public class BuildVersion {
        public static final String VERSION = "/version";
    }

    @UtilityClass
    public class Todos {
        public static final String TODOS = "/todos";
        public static final String TODO_BY_ID = "/todos/{id}";
    }
}
