package groupthree.gitruler;

import groupthree.gitruler.domain.Exercise;
import groupthree.gitruler.repository.ExerciseRepository;

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
   * This method catches the on ApplicationReadyEvent and inserts dummy data
   * into the exercise repository when caught.
   */
  @EventListener
  public void appReady(ApplicationReadyEvent event) {

    Exercise ex;
    
    for (int i = 0; i <= 10; i++) {
      ex = new Exercise();
      ex.setName("Exercise " + i);
      ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
          + "Aenean mattis vel purus et sagittis nullam.");
      exRepo.save(ex);
    }
      
  }

}
