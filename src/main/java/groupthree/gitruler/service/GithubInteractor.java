package groupthree.gitruler.service;

class GithubInteractor {

  boolean forkRepository(String forkUrl, String decryptedToken) {

    URI uri = new URI(forkUrl);

    RestTemplate oauth2RestTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + decryptedToken);

    try{
      oauth2RestTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), String.class);  
      return true;
    } catch (HttpClientErrorException exception) {
      exception.printStackTrace();
      return false;
    }
  }
}