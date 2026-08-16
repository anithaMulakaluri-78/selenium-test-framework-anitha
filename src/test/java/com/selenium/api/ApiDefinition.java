package com.selenium.api;

import java.util.Map;

public record ApiDefinition(
        String name,
        String method,
        String endpoint,
        Map<String, String> headers,
        Map<String, String> pathParams,
        Map<String, String> queryParams,
        String payloadFile,
        Integer expectedStatus,
        String expectedResponseFile,
        String schemaFile) {
}