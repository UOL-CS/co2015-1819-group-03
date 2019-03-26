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
    
    Exercise ex = new Exercise();
    ex.setName("Section A");
    ex.setDescription("Configuring git, committing updates to tracked "
        + "files and pushing changes to origin.");
    ex.setTheme("bythepool");
    ex.setPoint(30);
    ex.setRepository("https://gitlab.com/BrandonRNeath/gitruler-a-1");
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Section B");
    ex.setDescription("Tracking a new file, staging specific files for a commit "
        + "and checking the repository status.");
    ex.setTheme("heatwave");
    ex.setPoint(100);
    ex.setRepository("https://gitlab.com/BrandonRNeath/gitruler-b-1");
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Section C");
    ex.setDescription("Ignoring moving and removing files in git.");
    ex.setTheme("sugarsweets");
    ex.setPoint(100);
    ex.setRepository("https://gitlab.com/BrandonRNeath/gitruler-section-c");
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Section D");
    ex.setDescription("Tagging and using git to view a log of commits "
        + "and differences between versions.");
    ex.setTheme("duskfalling");
    ex.setPoint(100);
    ex.setRepository("https://gitlab.com/BrandonRNeath/gitruler-d-1");
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Section E");
    ex.setDescription("Branching and merging and resolving merge conflicts.");
    ex.setTheme("bythepool");
    ex.setPoint(100);
    ex.setRepository("https://gitlab.com/BrandonRNeath/gitruler-e-1");
    exRepo.save(ex);
    
    ex = new Exercise();
    ex.setName("Practice A-E");
    ex.setDescription("Practice all the skills that you learned from previous exercises.");
    ex.setTheme("sugarsweets");
    ex.setPoint(100);
    ex.setRepository("https://gitlab.com/BrandonRNeath/gitruler-practice-a-e");
    exRepo.save(ex);

  }

}
