package groupthree.gitruler.repository;

import java.util.List;

import groupthree.gitruler.domain.Attempt;
import groupthree.gitruler.domain.Exercise;
import groupthree.gitruler.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface AttemptRepository extends CrudRepository<Attempt, Integer> {
  Attempt findById(int id);

  List<Attempt> findByExercise (Exercise exercise);

  List<Attempt> findByUser (User user);

  List<Attempt> findByExerciseAndUser(Exercise exercise, User user);

}