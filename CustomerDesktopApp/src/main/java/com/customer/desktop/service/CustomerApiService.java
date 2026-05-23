package com.customer.desktop.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.customer.desktop.model.Customer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class CustomerApiService {

    private static final String BASE_URL = "http://localhost:8080/api/customers";
    private static final String BEARER_TOKEN = "Bearer my-secret-token";

    private final HttpClient httpClient;
    private final Gson gson;

    public CustomerApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    //  Helper 
    private HttpRequest.Builder authorizedRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", BEARER_TOKEN)
                .header("Content-Type", "application/json");
    }

    // GET all with search & pagination 
    public List<Customer> searchCustomers(String name, String email, String phone, int page, int size) throws Exception {
        StringBuilder url = new StringBuilder(BASE_URL + "?page=" + page + "&size=" + size);
        if (name != null && !name.isEmpty()) url.append("&name=").append(name);
        if (email != null && !email.isEmpty()) url.append("&email=").append(email);
        if (phone != null && !phone.isEmpty()) url.append("&phone=").append(phone);

        HttpRequest request = authorizedRequest(url.toString()).GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200)
            throw new Exception("Failed to search customers: " + response.statusCode());

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        return gson.fromJson(jsonObject.get("content"), new TypeToken<List<Customer>>() {}.getType());
    }

    //  GET by ID 
    public Customer getCustomerById(int id) throws Exception {
        HttpRequest request = authorizedRequest(BASE_URL + "/" + id).GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200)
            throw new Exception("Customer not found");

        return gson.fromJson(response.body(), Customer.class);
    }

    //  POST
    public Customer createCustomer(Customer customer) throws Exception {
        String json = gson.toJson(customer);

        HttpRequest request = authorizedRequest(BASE_URL)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            // اقرأ الرسالة من الـ response body
            try {
                JsonObject error = JsonParser.parseString(response.body()).getAsJsonObject();
                throw new Exception(error.get("message").getAsString());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        return gson.fromJson(response.body(), Customer.class);
    }
    //  PUT 
    public Customer updateCustomer(int id, Customer customer) throws Exception {
        String json = gson.toJson(customer);

        HttpRequest request = authorizedRequest(BASE_URL + "/" + id)
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            try {
                JsonObject error = JsonParser.parseString(response.body()).getAsJsonObject();
                throw new Exception(error.get("message").getAsString());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        return gson.fromJson(response.body(), Customer.class);
    }

    //  DELETE 
    public void deleteCustomer(int id) throws Exception {
        HttpRequest request = authorizedRequest(BASE_URL + "/" + id)
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204)
            throw new Exception("Failed to delete customer: " + response.statusCode());
    }
}