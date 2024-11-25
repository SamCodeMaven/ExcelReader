package uz.xnarx.excelreader.caller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import uz.xnarx.excelreader.dto.ClientPassportDto;

/**
 * @author Samandar Daminov
 * Date: 11/15/2024
 */
@Slf4j
@Configuration
public class ClientCaller {

    @Value("${client-manager.update}")
    private String update;

    public final RestClient restClient;

    public ClientCaller(RestClient restClient) {
        this.restClient = restClient;
    }

    public String updateClientPassport(ClientPassportDto dto, String traceId) {
        try {
            HttpStatusCode statusCode = restClient.patch()
                    .uri(update)
                    .header("traceId", traceId)
                    .body(dto)
                    .retrieve()
                    .toBodilessEntity()
                    .getStatusCode();

            return statusCode.is2xxSuccessful() ? "Success" : "Failed with status: " + statusCode;
        } catch (RestClientResponseException ex) {
            String errorMessage = "Error updating client passport. Status: " + ex.getRawStatusCode() +
                    ", Body: " + ex.getResponseBodyAsString();
            log.error(errorMessage, ex);
            return errorMessage;
        } catch (RestClientException ex) {
            String errorMessage = "Error with REST client while updating client passport";
            log.error(errorMessage, ex);
            return errorMessage;
        } catch (Exception ex) {
            String errorMessage = "Unexpected error updating client passport";
            log.error(errorMessage, ex);
            return errorMessage;
        }
    }

}
