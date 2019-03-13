package groupthree.gitruler.service;

class OAuthService  {
  
  public String getTokenFromUser(Principle principal) {

    OAuth2AuthenticationToken authTokenObj = (OAuth2AuthenticationToken) principal;
    String userId = authTokenObj.getPrincipal().getAttributes().get("id").toString();
    
    User user = userRepo.findById(Integer.parseInt(userId.toString()));
    return = user.decryptToken(user.getToken(), encryptionPass);
  }
}