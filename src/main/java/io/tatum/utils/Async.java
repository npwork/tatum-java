package io.tatum.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class Async implements Serializable {

    private Async() {}

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String apiKey = ApiKey.getInstance().getApiKey();

    public static String post(String uri, Object body) throws ExecutionException, InterruptedException, IOException {
        String requestBody = objectMapper.writeValueAsString(body);
        return Async.post(uri, requestBody);
    }

    public static String post(String uri, String requestBody) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .headers("x-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode() == 200) {
                        return response.body();
                    }
                    return null;
                }).get();
    }

    public static <T> T post(String uri, String requestBody, Class<T> valueType) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .headers("x-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    System.out.println(response.body());
                    if (response.statusCode() == 200) {
                        try {
                            return objectMapper.readValue(response.body(), valueType);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    return null;
                }).get();
    }

    public static <T> T post(String uri, Object body, Class<T> valueType) throws ExecutionException, InterruptedException, IOException {
        String requestBody = objectMapper.writeValueAsString(body);
        return Async.post(uri, requestBody, valueType);
    }

    public static <T> T put(String uri, Object body, Class<T> valueType) throws JsonProcessingException, ExecutionException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(body);
        return Async.put(uri, requestBody, valueType);
    }

    public static <T> T put(String uri, String requestBody, Class<T> valueType) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .headers("x-api-key", apiKey)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    System.out.println(response.body());
                    if (response.statusCode() == 200) {
                        try {
                            return objectMapper.readValue(response.body(), valueType);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    return null;
                })
                .get();
    }

    public static String put(String uri, String requestBody) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .headers("x-api-key", apiKey)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    System.out.println(response.body());
                    return response;
                })
                .thenApply(HttpResponse::body).get();
    }

    public static <T> T get(String uri, Class<T> valueType) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .headers("x-api-key", apiKey)
                .GET()
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    System.out.println(response.body());
                    if (response.statusCode() == 200) {
                        try {
                            return objectMapper.readValue(response.body(), valueType);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    return null;
                }).get();
    }

    public static String get(String uri) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .headers("x-api-key", apiKey)
                .GET()
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    System.out.println(response.body());
                    if (response.statusCode() == 200) {
                        return response.body();
                    }
                    return null;
                }).get();
    }

    public static String getJson(String uri) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        return response.body();
                    }
                    return null;
                }).get();
    }

    public static HttpResponse get(String uri, String apiKey) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .headers("x-api-key", apiKey)
                .GET()
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    return response;
                }).get();
    }

    public static String delete(String uri) throws ExecutionException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .headers("x-api-key", apiKey)
                .DELETE()
                .build();

        var client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println(response.statusCode());
                    System.out.println(response);
                    return response;
                })
                .thenApply(HttpResponse::body).get();
    }
}
