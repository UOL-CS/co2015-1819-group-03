package groupthree.gitruler.repository;

import groupthree.gitruler.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
  User findById(int id);
}
