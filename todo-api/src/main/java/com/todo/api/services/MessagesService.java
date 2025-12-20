package com.todo.api.services;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@CommonsLog
@AllArgsConstructor
public class MessagesService {

    private final MessageSource messageSource;

    public String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (Exception ex) {
            log.warn(String.format("No message found under key '%s' for locale '%s'", key, locale.toLanguageTag()));
        }
        return key;
    }

}
