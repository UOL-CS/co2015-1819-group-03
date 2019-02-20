package groupthree.gitruler.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  /**
   * Handles the route for the index page. Redirect to exercises page if
   * authenticated.
   */
  @RequestMapping("/")
  public String index() {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (!(auth instanceof AnonymousAuthenticationToken)) {
      return "redirect:/exercises";
    }

    return "index";
  }

  /**
   * Handles the route for the exercises page containing the list of exercises.
   */
  @RequestMapping("/exercises")
  public String exerciseList() {
    return "exercises";
  }

}
