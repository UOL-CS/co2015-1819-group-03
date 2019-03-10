package groupthree.gitruler.security;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.util.StringUtils;

public class AccessResponseBody {
  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("expires_in")
  private int expiresIn;

  @JsonProperty("refresh_token")
  private String refreshToken;

  private String scope;
  
  public AccessResponseBody() {}

  /**
   * Constructor for the response of requesting an OAuth2 access token from a REST API.
   * Response contains an access token, a token type, an expires in time and a refresh token.
   */
  public AccessResponseBody(String accessToken, String tokenType, int expiresIn, 
      String refreshToken, String scope) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.refreshToken = refreshToken;
    this.scope = scope;
  }

  public String getAccessToken() {
    return accessToken;
  }
  
  /**
   * Returns token type of the response.
   * OAuth2 uses Authorization: Bearer token type to authorize requests.
   */
  public OAuth2AccessToken.TokenType getTokenType() {
    String type = OAuth2AccessToken.TokenType.BEARER.getValue();
    if (type.equalsIgnoreCase(tokenType)) {
      return OAuth2AccessToken.TokenType.BEARER;
    }
    return null;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  /**
   * Parses the scope from a given string containing the scope.
   * Return empty set if response doesn't contain a scope.
   */
  public Set<String> getScopes() {
    if (StringUtils.isEmpty(scope)) {
      return Collections.emptySet();
    }
    return Stream.of(scope.split("\\s+")).collect(Collectors.toSet());
  }
}
