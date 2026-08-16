package com.selenium.api;

import java.util.Map;

public record ApiDefinition(
        String name,
        String method,
        String endpoint,
        Map<String, String> headers,
        String payloadFile,
        Integer expectedStatus,
        String expectedResponseFile,
        String schemaFile) {
}