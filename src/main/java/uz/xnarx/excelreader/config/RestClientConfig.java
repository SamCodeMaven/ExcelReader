package uz.xnarx.excelreader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;


/**
 * @author Samandar Daminov
 * Date: 11/15/2024
 */
@Configuration
public class RestClientConfig {
    @Value("${client-manager.url}")
    private String clientManagerUrl;

    @Bean("passport")
    public RestClient updatePassport(){
        return RestClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(clientManagerUrl)
                .build();
    }
}
