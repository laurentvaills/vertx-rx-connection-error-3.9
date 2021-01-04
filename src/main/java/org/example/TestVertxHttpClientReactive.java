package org.example;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.WriteStreamSubscriber;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.http.HttpClient;
import io.vertx.reactivex.core.http.HttpClientRequest;

public class TestVertxHttpClientReactive {

    public static void sendRequest(Vertx vertx) {

        HttpClient httpClient = vertx.createHttpClient();

        HttpClientRequest request = httpClient.request(HttpMethod.POST, 1234, "badhost", "/")
                                              .handler(httpClientResponse -> {
                                                  System.out.println("Response status code: " + httpClientResponse.statusCode());
                                              })
                                              .setChunked(true);

        WriteStreamSubscriber<Buffer> subscriber = request.toSubscriber();
        subscriber
                .onError(throwable -> {
                    System.out.println("onError ");
                    throwable.printStackTrace();
                })
                .onWriteStreamError(throwable -> {
                    System.out.println("onWriteStreamError ");
                    throwable.printStackTrace();
                })
                .onWriteStreamEndError(throwable -> {
                    System.out.println("onWriteStreamEndError ");
                    throwable.printStackTrace();
                })
                .onWriteStreamEnd(() -> System.out.println("onWriteStreamEnd"));

        bufferPublisher().subscribe(subscriber);
    }

    private static Publisher<Buffer> bufferPublisher() {
        return Flowable.empty();
        // Uncommenting the following make the connection error reported through onWriteStreamError
        // return Flowable.just(buffer("Hello Rx-Vert.x")).repeat(48_000);
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        sendRequest(vertx);
    }
}

