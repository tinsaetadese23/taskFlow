package com.ttk.taskflow.controller;

import com.ttk.taskflow.dto.TaskRequest;
import com.ttk.taskflow.enums.TaskStatus;
import com.ttk.taskflow.model.Task;
import com.ttk.taskflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<Task>> listTasks(Principal principal) {
        return ResponseEntity.ok(taskService.getAllTasksForUser(principal.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> editTask(
            @PathVariable Long id,
            @RequestBody TaskRequest request,
            Principal principal) {
        return ResponseEntity.ok(taskService.update(id, request, principal.getName()));
    }

    // Special action: Change Status quickly
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> changeStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status,
            Principal principal) {
        return ResponseEntity.ok(taskService.updateStatus(id, status, principal.getName()));
    }
}
