package org.masteukodeu.robocoop.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class Clock {
    public LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
    }
}
