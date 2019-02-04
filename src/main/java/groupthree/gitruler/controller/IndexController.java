package groupthree.gitruler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  @RequestMapping("/")
  public String index(Model model) {
    return "index";
  }

  @RequestMapping("/exercises")
  public String exerciseList(Model model) {
    return "exercises";
  }

}
