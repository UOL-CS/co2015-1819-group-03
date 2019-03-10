package groupthree.gitruler.security;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails.UserInfoEndpoint;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import groupthree.gitruler.domain.User;
import groupthree.gitruler.repository.UserRepository;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private RestOperations restOperations;
  private UserRepository userRepo;

  public CustomOAuth2UserService(RestOperations restOperations, UserRepository userRepo) {
    this.restOperations = restOperations;    
    this.userRepo = userRepo;
  }  
 
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    UserInfoEndpoint userInfoEndpoint = 
        userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint();
    
    String userInfoUrl = userInfoEndpoint.getUri();
    String userNameAttributeName = userInfoEndpoint.getUserNameAttributeName();

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, 
        String.format("Bearer %s", userRequest.getAccessToken().getTokenValue()));

    ParameterizedTypeReference<Map<String, Object>> typeReference = 
        new ParameterizedTypeReference<Map<String, Object>>() {};
        
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

    ResponseEntity<Map<String, Object>> responseEntity = 
        restOperations.exchange(userInfoUrl, HttpMethod.GET, entity, typeReference);

    Map<String, Object> userAttributes = responseEntity.getBody();
    
    Set<GrantedAuthority> authorities = 
        Collections.singleton(new OAuth2UserAuthority(userAttributes));
    
    int userId = Integer.parseInt(userAttributes.get("id").toString());
    String userToken = userRequest.getAccessToken().getTokenValue();
    User user = userRepo.findById(userId);
    
    user = new User();
    user.setId(userId);
    user.setToken(userToken);

    userRepo.save(user);
    
    return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);  
  }
}