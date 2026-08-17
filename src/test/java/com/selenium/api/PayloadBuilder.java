package com.selenium.api;

import java.util.Map;

/** Replaces CSV variables in a JSON payload template while preserving JSON types for numbers/booleans. */
public final class PayloadBuilder {

    private PayloadBuilder() {
    }

    public static String build(String template, Map<String, String> data) {
        String result = template;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = "${" + entry.getKey() + "}";
            String value = entry.getValue();

            // For placeholders surrounded by JSON quotes, remove the quotes for numeric/boolean CSV values.
            if (value != null && (value.matches("-?\\d+(\\.\\d+)?")
                    || "true".equalsIgnoreCase(value)
                    || "false".equalsIgnoreCase(value)
                    || "null".equalsIgnoreCase(value))) {
                result = result.replace("\"" + key + "\"", value);
            } else {
                result = result.replace(key, escapeJson(value));
            }
        }
        return JsonUtils.resolveDynamicValues(result);
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }
}
