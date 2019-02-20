package groupthree.gitruler.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import groupthree.gitruler.domain.Exercise;

public interface ExerciseRepository extends CrudRepository <Exercise,Integer> {
  Exercise findById(int id);
  List<Exercise> findByName(String name);
  List<Exercise> findByDescription(String description);
}
