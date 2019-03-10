package groupthree.gitruler.security;

import java.util.Collections;
import java.util.Set;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

public class CustomOAuth2AccessTokenResponseClient
    implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

  private RestOperations restOperations;

  public CustomOAuth2AccessTokenResponseClient(RestOperations restOperations) {
    this.restOperations = restOperations;
  }
  
  @Override
  public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest
      authorizationGrantRequest) throws OAuth2AuthenticationException {  
    
    ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
    String tokenUri = clientRegistration.getProviderDetails().getTokenUri();
    
    String clientId = clientRegistration.getClientId();
    String clientSecret = clientRegistration.getClientSecret();
    String grantType = clientRegistration.getAuthorizationGrantType().getValue();
    String code = authorizationGrantRequest
                        .getAuthorizationExchange().getAuthorizationResponse().getCode();
    String uri = authorizationGrantRequest
                        .getAuthorizationExchange().getAuthorizationRequest().getRedirectUri();
    Set<String> scope = authorizationGrantRequest.getClientRegistration().getScopes();

    MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<String, String>();
    tokenRequest.add("client_id", clientId);
    tokenRequest.add("client_secret", clientSecret);
    tokenRequest.add("grant_type", grantType);
    tokenRequest.add("code", code);
    tokenRequest.add("redirect_uri", uri);
    tokenRequest.add("scope", String.join(" ", scope));
    
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(tokenRequest, headers);

    ResponseEntity<AccessResponseBody> response =
        restOperations.exchange(tokenUri, HttpMethod.POST, entity, AccessResponseBody.class);
        
    AccessResponseBody accessResponseBody = new AccessResponseBody();
    Set<String> scopes = Collections.emptySet();
    
    if (response != null) {
      accessResponseBody = response.getBody();
      
      if (accessResponseBody.getScopes().isEmpty()) {
        scopes = scope;
      } else {
        scopes = accessResponseBody.getScopes();
      }
    }
    
    return OAuth2AccessTokenResponse.withToken(accessResponseBody.getAccessToken())
        .tokenType(accessResponseBody.getTokenType())
        .expiresIn(accessResponseBody.getExpiresIn())
        .scopes(scopes)
        .build();
  }

}
