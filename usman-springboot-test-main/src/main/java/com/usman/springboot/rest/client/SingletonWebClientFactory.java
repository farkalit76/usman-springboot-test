package com.usman.springboot.rest.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usman.springboot.exception.SSLCertificateException;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.http.codec.xml.Jaxb2XmlEncoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * @author : FUsman
 * @description: This class is to explore ....
 * @date : 30-11-2022
 * @since : 1.0.0
 */
@Slf4j
@UtilityClass
public class SingletonWebClientFactory {

    public static final int MAX_MEMORY_SIZE = 20 * 1024 * 1024;
    private static final String SSL_ERROR_MSG = "SSL certificate cannot be by-passed.";

    private static final String THIRD_PARTY_INTEGRATION_WITH_REQUEST = "Third Party Integration with request:::{}";

    /**
     * This method creates and returns WebClient object, that support XML marshaling/un-marshaling
     * using JAXb.
     *
     * @return {@link WebClient}
     */
    public static WebClient xmlWebClient() {
        return XmlWebClient.xmlWebClient;
    }

    /**
     * This method creates and returns WebClient object, that support only json.
     *
     * @return {@link WebClient}
     */
    public static WebClient webClient() {
        return JsonWebClient.jsonWebClient;
    }

    /**
     * @return
     */
    public static WebClient customizedWebClient() {
        return CustomizedJsonWebClient.customWebClient;
    }

    private static HttpClient httpClient() {
        try {
            SslContext sslContext =
                    SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

            return HttpClient.create().secure(t -> t.sslContext(sslContext));
        } catch (Exception e) {
            log.error("Exception occurred while bypassing SSL certs.", e);
            throw new SSLCertificateException(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), SSL_ERROR_MSG);
        }
    }

    private static class XmlWebClient {
        private static final WebClient xmlWebClient =
                WebClient.builder()
                        .exchangeStrategies(
                                ExchangeStrategies.builder()
                                        .codecs(
                                                codecConfigurer -> {
                                                    codecConfigurer.defaultCodecs().jaxb2Encoder(new Jaxb2XmlEncoder());
                                                    codecConfigurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder());
                                                    codecConfigurer.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE);
                                                })
                                        .build())
                        .clientConnector(new ReactorClientHttpConnector(httpClient()))
                        .filter(
                                (request, function) -> {
                                    log.info(THIRD_PARTY_INTEGRATION_WITH_REQUEST, request);
                                    return function.exchange(request);
                                })
                        .build();
    }

    private static class JsonWebClient {
        private static final WebClient jsonWebClient =
                WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(httpClient()))
                        .codecs(
                                codecConfigurer -> codecConfigurer.defaultCodecs().maxInMemorySize(MAX_MEMORY_SIZE))
                        .filter(
                                (request, function) -> {
                                    log.info(THIRD_PARTY_INTEGRATION_WITH_REQUEST, request);
                                    return function.exchange(request);
                                })
                        .build();
    }

    private static class CustomizedJsonWebClient {
        private static final WebClient customWebClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .filter((request, function) -> {
                    log.info(THIRD_PARTY_INTEGRATION_WITH_REQUEST, request);
                    return function.exchange(request);
                })
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    configurer.customCodecs().decoder(new Jackson2JsonDecoder(mapper,
                            MimeTypeUtils.parseMimeType(MediaType.APPLICATION_OCTET_STREAM_VALUE)));
                }).build())
                .build();
    }
}
