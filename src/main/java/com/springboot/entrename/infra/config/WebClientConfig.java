package com.springboot.entrename.infra.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Value("${laravel.api.endpoint}")
    private String laravelEndpoint;

    @Bean
    public WebClient laravelWebClient() {
        // Configurando HttpClient
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 4000)
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(4000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(4000, TimeUnit.MILLISECONDS));
            });

        // Construyendo el WebClient usando ReactorClientHttpConnector
        return WebClient.builder()
            .baseUrl(laravelEndpoint)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }
}
