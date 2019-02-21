package groupthree.gitruler.repository;

import groupthree.gitruler.domain.Exercise;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ExerciseRepository extends CrudRepository<Exercise,Integer> {
  Exercise findById(int id);
  
  List<Exercise> findByName(String name);
  
  List<Exercise> findByDescription(String description);
}
