package com.ttk.taskflow.controller;

import com.ttk.taskflow.dto.CategoryRequest;
import com.ttk.taskflow.dto.CategoryResponse;
import com.ttk.taskflow.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getMyCategories(Principal principal) {
        return ResponseEntity.ok(categoryService.getUserCategories(principal.getName()));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(
            @RequestBody CategoryRequest request,
            Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.addCategory(request, principal.getName()));
    }
}