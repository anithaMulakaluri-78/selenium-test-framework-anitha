package com.selenium.test;

import com.selenium.api.ApiClient;
import org.testng.annotations.Test;

import java.io.IOException;

public class ApiTest extends BaseApiTest {

    private final ApiClient apiClient = new ApiClient();

    @Test
    public void getBookingTest() throws IOException {
        apiClient.execute("getBooking.json");
    }

    @Test
    public void createBookingTest() throws IOException {
        apiClient.execute("createBooking.json");
    }
}