package com.licenta.shmafaerserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class ExceptionResponse {
    private Map<String, String> errors = new HashMap<>();
    private LocalDateTime dateTime;
}
