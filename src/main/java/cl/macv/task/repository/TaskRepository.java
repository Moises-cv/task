package cl.macv.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.macv.task.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
