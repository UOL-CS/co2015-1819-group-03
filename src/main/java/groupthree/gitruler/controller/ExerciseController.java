package groupthree.gitruler.controller;

import groupthree.gitruler.domain.Exercise;
import groupthree.gitruler.domain.User;
import groupthree.gitruler.repository.ExerciseRepository;
import groupthree.gitruler.repository.UserRepository;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Principal;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {
  
  @Autowired
  private UserRepository userRepo;
  
  @Value("${jasypt.encryptor.password}")
  private String encryptionPass;

  @Autowired
  private ExerciseRepository exRepo;

  /**
   * Handles the route for each singular exercise page. The id is retrieved from
   * the URL and is used to query the exercise repository.
   */
  @RequestMapping("/{id}")
  public String exercise(@PathVariable int id, Model model, Principal principal) 
      throws UnsupportedEncodingException, URISyntaxException, JSONException {
    
    try {
      OAuth2AuthenticationToken authTokenObj = (OAuth2AuthenticationToken) principal;
      String userId = authTokenObj.getPrincipal().getAttributes().get("id").toString();
      
      User user = userRepo.findById(Integer.parseInt(userId.toString()));
      String decryptedToken = user.decryptToken(user.getToken(), encryptionPass);
      
      RestTemplate oauth2RestTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + decryptedToken);
         
      Exercise e = exRepo.findById(id);
      
      int slashIndex = e.getRepository().lastIndexOf('/');
      String repo = null;
      if (slashIndex + 1 < e.getRepository().length()) {
        repo = e.getRepository().substring(slashIndex + 1);
      }
          
      String searchRepoUrl = "https://gitlab.com/api/v4/users/" + userId + "/projects?search=" + repo;
      URI uri = new URI(searchRepoUrl);
      
      ResponseEntity<String> result = null;
      HttpEntity<Object> entity = new HttpEntity<>(headers);
      result = oauth2RestTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
      boolean isForked = false;
      String repoLink = "";
      if (result != null) {
        String resultBody = result.getBody();
        JSONArray arr = new JSONArray(resultBody);
        if (arr.length() == 0) { 
          isForked = false;
        } else {
          isForked = true;
          repoLink = arr.getJSONObject(0).getString("http_url_to_repo");
        }
      }
      
      model.addAttribute("isForked", isForked);
      model.addAttribute("repoLink", repoLink);
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
    
    Exercise e = exRepo.findById(id);
    model.addAttribute("exercise", e);

    URL url = e.getReadmeUrl();
    String contents = e.getReadmeContents(url);
    model.addAttribute("instruction", e.renderMarkdown(contents));

    return "exercise";
  }
  
  /**
   * Handles the route for POST request of each singular exercise.
   * Forks the exercise repository for the user and redirects back to 
   * exercise page.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public String start(@PathVariable int id, 
      Principal principal, RedirectAttributes redirectAttributes) 
          throws UnsupportedEncodingException, URISyntaxException {
    
    OAuth2AuthenticationToken authTokenObj = (OAuth2AuthenticationToken) principal;
    String userId = authTokenObj.getPrincipal().getAttributes().get("id").toString();
    
    User user = userRepo.findById(Integer.parseInt(userId.toString()));
    String decryptedToken = user.decryptToken(user.getToken(), encryptionPass);
    
    RestTemplate oauth2RestTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + decryptedToken);
    
    Exercise e = exRepo.findById(id);
    String repoName = e.getRepository().replaceAll(".+(?<=com\\/)", "");
    
    String forkUrl = "https://gitlab.com/api/v4/projects/{project}/fork";
    forkUrl = forkUrl.replace("{project}", URLEncoder.encode(repoName, "UTF-8"));
    URI uri = new URI(forkUrl);
    
    oauth2RestTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), String.class);
    redirectAttributes.addFlashAttribute("isSuccessful", "true");
    
    return "redirect:/exercise/" + id;
  }
}
