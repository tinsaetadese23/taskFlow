package com.ttk.taskflow.repository;

import com.ttk.taskflow.model.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<TaskCategory, Long>{
    List<TaskCategory> findByUserUsername(String username);
}
