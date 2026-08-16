package com.selenium.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{([^}]+)}");

    private JsonUtils() {
    }

    /** Reads a test resource from the classpath; works on Windows and packaged resources. */
    public static String read(String classpathFile) throws IOException {
        try (InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(classpathFile)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found on classpath: " + classpathFile);
            }
            return new String(inputStream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        }
    }

    public static JsonNode readJson(String classpathFile) throws IOException {
        return MAPPER.readTree(read(classpathFile));
    }

    public static String resolveDynamicValues(String json) {
        Matcher matcher = PLACEHOLDER.matcher(json);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String expression = matcher.group(1);
            String value = switch (expression) {
                case "uuid" -> UUID.randomUUID().toString();
                case "timestamp" -> Instant.now().toString();
                case "randomEmail" -> "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
                case "randomName" -> "User_" + UUID.randomUUID().toString().substring(0, 8);
                default -> matcher.group(0);
            };
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public static boolean structurallyEqual(String expected, String actual) throws IOException {
        return MAPPER.readTree(expected).equals(MAPPER.readTree(actual));
    }

    public static String replaceVariables(String json, Map<String, String> variables) {
        String result = json;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }
}