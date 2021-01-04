package org.example;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;

/**
 * Main class to show that the connection error is reported correctly through the exceptionHandler.
 * No use of rx-ified Vert.x API.
 */
public class TestVertxHttpClient {

    public static void sendRequest(Vertx vertx) {
        HttpClient httpClient = vertx.createHttpClient();

        httpClient.request(HttpMethod.GET, 1234, "localhost", "/")
                  .handler(httpClientResponse -> {
                      System.out.println("Got response: " + httpClientResponse.statusCode());
                  })
                  .exceptionHandler(throwable -> {
                      System.out.println("exceptionHandler");
                      throwable.printStackTrace();
                  })
//                  .setChunked(true)
//                  .write(buffer("Hello Rx-Vert.x"))
                  .end();
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        sendRequest(vertx);
    }
}

