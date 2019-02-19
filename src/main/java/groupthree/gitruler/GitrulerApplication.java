package groupthree.gitruler;

import groupthree.gitruler.domain.Exercise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class contains the main method that is called by Spring to run the
 * application.
 */
@SpringBootApplication
public class GitrulerApplication implements CommandLineRunner {

  private static List<Exercise> exercises = new ArrayList<>();

  public static void main(String[] args) {
    SpringApplication.run(GitrulerApplication.class, args);
  }

  public static List<Exercise> getExercises() {
    return exercises;
  }

  public static void setExercises(List<Exercise> exercises) {
    GitrulerApplication.exercises = exercises;
  }

  public void run(String... args) {
    initialise();
  }

  /**
   * This method adds dummy exercises to the temporary exercise list.
   */
  public static void initialise() {
    Exercise exercise;

    for (int i = 1; i < 4; i++) {
      exercise = new Exercise();
      exercise.setId(i);
      exercise.setName("Exercise " + i);
      exercise.setDescription("description for the exercise number " + i);
      exercises.add(exercise);
    }
  }

}
