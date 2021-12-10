package io.tatum.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * The type Async.
 */
@Log4j2
public class Async implements Serializable {

    private Async() {}

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String apiKey = ApiKey.getInstance().getApiKey();

    /**
     * Post string.
     *
     * @param uri  the uri
     * @param body the body
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    public static String post(String uri, Object body) throws ExecutionException, InterruptedException, IOException {
        String requestBody = objectMapper.writeValueAsString(body);
        return Async.post(uri, requestBody);
    }

    /**
     * Post string.
     *
     * @param uri         the uri
     * @param requestBody the request body
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
                    log.info(response.statusCode());
                    if (response.statusCode() == 200) {
                        return response.body();
                    }
                    return null;
                }).get();
    }

    /**
     * Post t.
     *
     * @param <T>         the type parameter
     * @param uri         the uri
     * @param requestBody the request body
     * @param valueType   the value type
     * @return the t
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
                    log.info(response.statusCode());
                    log.info(response.body());
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

    /**
     * Post t.
     *
     * @param <T>       the type parameter
     * @param uri       the uri
     * @param body      the body
     * @param valueType the value type
     * @return the t
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    public static <T> T post(String uri, Object body, Class<T> valueType) throws ExecutionException, InterruptedException, IOException {
        String requestBody = objectMapper.writeValueAsString(body);
        return Async.post(uri, requestBody, valueType);
    }

    /**
     * Put t.
     *
     * @param <T>       the type parameter
     * @param uri       the uri
     * @param body      the body
     * @param valueType the value type
     * @return the t
     * @throws JsonProcessingException the json processing exception
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     */
    public static <T> T put(String uri, Object body, Class<T> valueType) throws JsonProcessingException, ExecutionException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(body);
        return Async.put(uri, requestBody, valueType);
    }

    /**
     * Put t.
     *
     * @param <T>         the type parameter
     * @param uri         the uri
     * @param requestBody the request body
     * @param valueType   the value type
     * @return the t
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
                    log.info(response.statusCode());
                    log.info(response.body());
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

    /**
     * Put string.
     *
     * @param uri         the uri
     * @param requestBody the request body
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
                    log.info(response.statusCode());
                    log.info(response.body());
                    return response;
                })
                .thenApply(HttpResponse::body).get();
    }

    /**
     * Get t.
     *
     * @param <T>       the type parameter
     * @param uri       the uri
     * @param valueType the value type
     * @return the t
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
                    if (response.statusCode() == 200) {
                        try {
                            // @TODO
                            String body = response.body();
                            if (body == null || body.equals(""))
                                return null;

                            return objectMapper.readValue(body, valueType);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    log.info(response.statusCode());
                    log.info(response.body());
                    return null;
                }).get();
    }

    /**
     * Get string.
     *
     * @param uri the uri
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
                    log.info(response.statusCode());
                    log.info(response.body());
                    if (response.statusCode() == 200) {
                        return response.body();
                    }
                    return null;
                }).get();
    }

    /**
     * Gets json.
     *
     * @param uri the uri
     * @return the json
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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

    /**
     * Get http response.
     *
     * @param uri    the uri
     * @param apiKey the api key
     * @return the http response
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
                    log.info(response.statusCode());
                    return response;
                }).get();
    }

    /**
     * Delete string.
     *
     * @param uri the uri
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
                    log.info(response.statusCode());
                    log.info(response);
                    return response;
                })
                .thenApply(HttpResponse::body).get();
    }
}
