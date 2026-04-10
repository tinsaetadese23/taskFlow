package com.ttk.taskflow.service;

import com.ttk.taskflow.dto.TaskRequest;
import com.ttk.taskflow.enums.TaskPriority;
import com.ttk.taskflow.enums.TaskStatus;
import com.ttk.taskflow.model.Task;
import com.ttk.taskflow.model.TaskCategory;
import com.ttk.taskflow.model.User;
import com.ttk.taskflow.repository.CategoryRepository;
import com.ttk.taskflow.repository.TaskRepository;
import com.ttk.taskflow.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasksForUser(String username) {
        return taskRepository.findByUserUsername(username);
    }

    public Task createTask(TaskRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TaskCategory category = null;
        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(TaskStatus.valueOf(request.status().toUpperCase()))
                .priority(TaskPriority.valueOf(request.priority().toUpperCase()))
                .dueDate(request.dueDate())
                .user(user)
                .category(category)
                .build();

        return taskRepository.save(task);
    }

    public Task update(Long taskId, TaskRequest request, String username) {
        Task task = taskRepository.findByIdAndUserUsername(taskId, username)
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(TaskStatus.valueOf(request.status().toUpperCase()));
        task.setPriority(TaskPriority.valueOf(request.priority().toUpperCase()));
        task.setDueDate(request.dueDate());

        if (request.categoryId() != null) {
            TaskCategory category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateStatus(Long taskId, TaskStatus status, String username) {
        Task task = taskRepository.findByIdAndUserUsername(taskId, username)
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));

        task.setStatus(status);

        return taskRepository.save(task);
    }
}
