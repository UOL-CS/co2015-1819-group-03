package groupthree.gitruler.repository;

import org.springframework.data.repository.CrudRepository;

import groupthree.gitruler.domain.User;

public interface UserRepository extends CrudRepository <User,Integer>{
  User findById(int id);
}
