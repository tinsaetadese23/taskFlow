package com.ttk.taskflow.service;

import com.ttk.taskflow.dto.CategoryRequest;
import com.ttk.taskflow.dto.CategoryResponse;
import com.ttk.taskflow.dto.UserResponse;
import com.ttk.taskflow.model.TaskCategory;
import com.ttk.taskflow.repository.CategoryRepository;
import com.ttk.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<CategoryResponse> getUserCategories(String username) {
        return categoryRepository.findByUserUsername(username).stream()
                .map(category -> new CategoryResponse(category.getId(), category.getCategory_name(), category.getCategory_description()))
                .toList();
    }

    public CategoryResponse addCategory(CategoryRequest request, String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        var category = TaskCategory.builder()
                .category_name(request.categoryName())
                .category_description(request.categoryDescription())
                .user(user)
                .build();
        var savedCategory = categoryRepository.save(category);
        return new CategoryResponse(savedCategory.getId(), savedCategory.getCategory_name(), savedCategory.getCategory_description());

    }
}

