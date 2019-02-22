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
    
    ex = new Exercise();
    ex.setName("Exercise 1");
    ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        + "Aenean mattis vel purus et sagittis nullam.");
    ex.setIcon("http://tinygraphs.com/labs/isogrids/hexa/Exercise 1?theme=seascape&numcolors=4&size=220&fmt=svg");
    ex.setPoint(250);
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Exercise 2");
    ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        + "Aenean mattis vel purus et sagittis nullam.");
    ex.setIcon("http://tinygraphs.com/labs/isogrids/hexa/Exercise 2?theme=bythepool&numcolors=4&size=220&fmt=svg");
    ex.setPoint(200);
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Exercise 3");
    ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        + "Aenean mattis vel purus et sagittis nullam.");
    ex.setIcon("http://tinygraphs.com/labs/isogrids/hexa/Exercise 3?theme=heatwave&numcolors=4&size=220&fmt=svg");
    ex.setPoint(300);
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Exercise 4");
    ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        + "Aenean mattis vel purus et sagittis nullam.");
    ex.setIcon("http://tinygraphs.com/labs/isogrids/hexa/Exercise 4?theme=duskfalling&numcolors=4&size=220&fmt=svg");
    ex.setPoint(100);
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Exercise 5");
    ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        + "Aenean mattis vel purus et sagittis nullam.");
    ex.setIcon("http://tinygraphs.com/labs/isogrids/hexa/Exercise 5?theme=summerwarmth&numcolors=4&size=220&fmt=svg");
    ex.setPoint(250);
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Exercise 6");
    ex.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        + "Aenean mattis vel purus et sagittis nullam.");
    ex.setIcon("http://tinygraphs.com/labs/isogrids/hexa/Exercise 6?theme=duskfalling&numcolors=4&size=220&fmt=svg");
    ex.setPoint(200);
    exRepo.save(ex);   
    
  }
      
}
