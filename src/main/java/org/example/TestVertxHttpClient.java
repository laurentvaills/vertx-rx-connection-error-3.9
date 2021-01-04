package org.example;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;

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

