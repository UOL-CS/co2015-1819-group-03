package groupthree.gitruler.controller;

import groupthree.gitruler.domain.Exercise;
import groupthree.gitruler.repository.ExerciseRepository;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/exercise")
public class ExerciseController {

  @Autowired
  private ExerciseRepository exRepo;

  /**
   * Handles the route for each singular exercise page. The id is retrieved from
   * the URL and is used to query the exercise repository.
   */
  @RequestMapping("/{id}")
  public String exercise(@PathVariable int id, Model model) {
    Exercise e = exRepo.findById(id);

    model.addAttribute("exercise", e);

    URL url = e.getReadmeUrl();
    String contents = e.getReadmeContents(url);
    model.addAttribute("instruction", e.renderMarkdown(contents));

    return "exercise";
  }


}
