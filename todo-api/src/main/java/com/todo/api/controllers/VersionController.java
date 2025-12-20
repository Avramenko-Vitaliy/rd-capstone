package com.todo.api.controllers;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RestController
@RequestMapping(Api.ROOT_PATH)
public class VersionController {

    @Value("${app.version:unknown}")
    private String version;

    @GetMapping(Api.BuildVersion.VERSION)
    public String getVersion() {
        return this.version;
    }
}
