package at.spengergasse.mate.repository;

import at.spengergasse.mate.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TaskRepository extends JpaRepository<Task, Long> {
    public Task findById(long id);
}
