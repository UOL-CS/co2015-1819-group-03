package groupthree.gitruler.controller;

import groupthree.gitruler.domain.Exercise;
import groupthree.gitruler.repository.ExerciseRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
  
  @Autowired
  private ExerciseRepository exRepo;

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
  public String exerciseList(Model model) {
    
    List<Exercise> exercises;
    exercises = (List<Exercise>) exRepo.findAll();

    if (exercises.isEmpty()) {
      model.addAttribute("isEmpty", exercises.isEmpty());
    } else {
      model.addAttribute("exercises", exercises);
    }
    
    return "exercises";
  }

}
