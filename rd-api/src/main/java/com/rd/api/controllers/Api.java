package com.rd.api.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Api {

    public static final String ROOT_PATH = "/rd-api";

    @UtilityClass
    public class BuildVersion {
        public static final String VERSION = "/version";
    }

    @UtilityClass
    public class Todos {
        public static final String TODOS = "/todos";
    }
}
