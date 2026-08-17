package com.selenium.test;

import com.selenium.api.ApiClient;
import com.selenium.utils.CsvDataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class ApiTest extends BaseApiTest {
    private final ApiClient apiClient = new ApiClient();

    @Test
    public void getBookingTest() throws IOException {
        apiClient.execute("getBooking.json");
    }

    @Test(dataProvider = "createBookingData", dataProviderClass = CsvDataProvider.class)
    public void createBookingTest(Map<String, String> testData) throws IOException {
        apiClient.execute("createBooking.json", testData);
    }
}
