package com.selenium.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiClient {
    private final ObjectMapper mapper = new ObjectMapper();

    public Response execute(String definitionFile) throws IOException {
        return execute(definitionFile, Map.of());
    }

    public Response execute(String definitionFile, Map<String, String> testData) throws IOException {
        ApiDefinition definition = mapper.readValue(JsonUtils.read("api/definitions/" + definitionFile), ApiDefinition.class);
        String endpoint = definition.endpoint();
        if (definition.pathParams() != null) {
            for (Map.Entry<String, String> param : definition.pathParams().entrySet()) {
                endpoint = endpoint.replace("{" + param.getKey() + "}", param.getValue());
            }
        }

        String payload = null;
        if (definition.payloadFile() != null && !definition.payloadFile().isBlank()) {
            payload = PayloadBuilder.build(JsonUtils.read("api/payloads/" + definition.payloadFile()), testData);
        }

        System.out.println("\n========== API REQUEST ==========");
        System.out.println("NAME   : " + definition.name());
        System.out.println("METHOD : " + definition.method());
        System.out.println("URL    : " + RestAssured.baseURI + endpoint);
        System.out.println("HEADERS: " + definition.headers());
        System.out.println("QUERY  : " + definition.queryParams());
        System.out.println("TEST DATA: " + testData);
        System.out.println("BODY   : " + (payload == null ? "<none>" : payload));
        System.out.println("=================================\n");

        RequestSpecification request = RestAssured.given();
        if (definition.headers() != null) {
            definition.headers().forEach(request::header);
        }
        if (definition.queryParams() != null) {
            definition.queryParams().forEach(request::queryParam);
        }
        if (payload != null) {
            request.body(payload);
        }

        Response response = switch (definition.method().toUpperCase()) {
            case "GET" -> request.get(endpoint);
            case "POST" -> request.post(endpoint);
            case "PUT" -> request.put(endpoint);
            case "PATCH" -> request.patch(endpoint);
            case "DELETE" -> request.delete(endpoint);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + definition.method());
        };

        System.out.println("========== API RESPONSE ==========");
        System.out.println("STATUS : " + response.statusCode());
        System.out.println("TIME   : " + response.time() + " ms");
        System.out.println("BODY   : " + response.asPrettyString());
        System.out.println("==================================\n");

        if (definition.expectedStatus() != null) response.then().statusCode(definition.expectedStatus());
        if (definition.schemaFile() != null && !definition.schemaFile().isBlank()) {
            response.then().body(matchesJsonSchemaInClasspath("api/schemas/" + definition.schemaFile()));
        }
        if (definition.expectedResponseFile() != null && !definition.expectedResponseFile().isBlank()) {
            String expected = JsonUtils.read("api/expected/" + definition.expectedResponseFile());
            if (!JsonUtils.structurallyEqual(expected, response.asString())) {
                throw new AssertionError("Expected JSON does not match actual JSON.\nExpected:\n" + expected + "\nActual:\n" + response.asPrettyString());
            }
        }
        return response;
    }
}
