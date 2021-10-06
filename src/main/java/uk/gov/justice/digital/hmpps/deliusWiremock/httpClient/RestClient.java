package uk.gov.justice.digital.hmpps.deliusWiremock.httpClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.gov.justice.digital.hmpps.deliusWiremock.httpClient.dto.AuthResponse;

@Component
public class RestClient {

  private final RestTemplate client;
  private final HttpHeaders headers;
  @Value("${auth.host}")
  private String authHost;
  @Value("${auth.clientId}")
  private String clientId;
  @Value("${auth.clientSecret}")
  private String clientSecret;

  public RestClient() {
    this.client = new RestTemplate();
    this.headers = new HttpHeaders();
    this.headers.add("Content-Type", "application/json");
  }

  public <T> List<T> getList(String url, ParameterizedTypeReference<List<T>> responseType) {
    getToken();
    HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
    return client.exchange(url, HttpMethod.GET, requestEntity, responseType).getBody();
  }

  public <T> T get(String url, Class<T> responseType) {
    getToken();
    HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
    return client.exchange(url, HttpMethod.GET, requestEntity, responseType).getBody();
  }

  private void getToken() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + Base64.getEncoder()
        .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8)));
    HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
    ResponseEntity<AuthResponse> responseEntity = client.exchange(
        authHost + "/oauth/token?grant_type=client_credentials", HttpMethod.POST,
        requestEntity, AuthResponse.class);
    this.headers.remove("Authorization");
    this.headers.add("Authorization",
        "Bearer " + Objects.requireNonNull(responseEntity.getBody()).getAccess_token());
  }
}
