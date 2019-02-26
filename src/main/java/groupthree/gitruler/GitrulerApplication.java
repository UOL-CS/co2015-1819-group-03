package groupthree.gitruler;

import groupthree.gitruler.domain.Exercise;
import groupthree.gitruler.repository.ExerciseRepository;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * This class contains the main method that is called by Spring to run the
 * application.
 */
@SpringBootApplication
public class GitrulerApplication {

  @Autowired
  private ExerciseRepository exRepo;

  public static void main(String[] args) {
    SpringApplication.run(GitrulerApplication.class, args);
  }

  /**
   * This method catches the on ApplicationReadyEvent and inserts dummy data into
   * the exercise repository when caught.
   */
  @EventListener
  public void appReady(ApplicationReadyEvent event) {

    String[] themes = { "sugarsweets", "heatwave", "daisygarden", "seascape", "summerwarmth", 
        "bythepool", "duskfalling", "frogideas", "berrypie" };
    
    String[] repositories = {
        "https://gitlab.com/gitlab-org/gitlab-ce",
        "https://gitlab.com/gitlab-org/gitlab-runner",
        "https://gitlab.com/inkscape/inkscape",
        "https://gitlab.com/gnachman/iterm2",
        "https://gitlab.com/gitlab-org/omnibus-gitlab",
        "https://gitlab.com/tortoisegit/tortoisegit"
    };
   
    Exercise ex;

    for (int i = 1; i <= 6; i++) {
      ex = new Exercise();
      ex.setName("Exercise " + i);
      ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " 
          + "Aenean mattis vel purus et sagittis nullam.");
      ex.setTheme(themes[new Random().nextInt(9)]);
      ex.setPoint((new Random().nextInt(41) + 10) * 10);
      ex.setRepository(repositories[i - 1]);
      
      exRepo.save(ex);
    }

  }

}
