package com.selenium.utils;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Reads CSV test data from src/test/resources and exposes it to TestNG. */
public final class CsvDataProvider {

    private CsvDataProvider() {
    }

    public static Object[][] read(String classpathFile) throws IOException {
        try (InputStream input = CsvDataProvider.class.getClassLoader().getResourceAsStream(classpathFile)) {
            if (input == null) {
                throw new IllegalArgumentException("CSV resource not found: " + classpathFile);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String headerLine = reader.readLine();
                if (headerLine == null || headerLine.isBlank()) {
                    throw new IllegalArgumentException("CSV is empty: " + classpathFile);
                }

                String[] headers = headerLine.split(",", -1);
                List<Object[]> rows = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.isBlank()) {
                        continue;
                    }
                    String[] values = line.split(",", -1);
                    if (values.length != headers.length) {
                        throw new IllegalArgumentException(
                                "CSV column count mismatch in " + classpathFile + ": " + line);
                    }

                    Map<String, String> data = new LinkedHashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        data.put(headers[i].trim(), values[i].trim());
                    }
                    rows.add(new Object[]{data});
                }

                return rows.toArray(new Object[0][]);
            }
        }
    }

    @DataProvider(name = "createBookingData")
    public static Object[][] createBookingData() throws IOException {
        return read("api/testdata/createBooking.csv");
    }
}
