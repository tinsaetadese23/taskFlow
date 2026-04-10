package com.ttk.taskflow.repository;

import com.ttk.taskflow.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>{

    List<Task> findByUserUsername(String username);
    Optional<Task> findByIdAndUserUsername(Long id, String username);
}
