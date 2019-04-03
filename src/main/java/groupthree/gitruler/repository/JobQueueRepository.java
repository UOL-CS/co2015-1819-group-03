package groupthree.gitruler.repository;

import groupthree.gitruler.domain.JobQueue;
import org.springframework.data.repository.CrudRepository;

public interface JobQueueRepository extends CrudRepository<JobQueue, Integer> {

}
