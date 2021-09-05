package com.reloadly.customeraccount.helpers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;

import java.time.Duration;

public class HttpRequest {

    public static <T> Mono<T> make(String endpoint, HttpMethod method, Object requestObject, Class<T> responseClass) {
        var webclient = WebClient
            .create()
            .method(method)
            .uri(endpoint)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        if(requestObject != null)
            webclient.body(BodyInserters.fromValue(requestObject));

        return webclient
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new HttpServerErrorException(clientResponse.statusCode(), clientResponse.statusCode().getReasonPhrase())))
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new HttpServerErrorException(clientResponse.statusCode(), clientResponse.statusCode().getReasonPhrase())))
            .bodyToMono(responseClass)
            .timeout(Duration.ofMillis(20000));
    }


}
