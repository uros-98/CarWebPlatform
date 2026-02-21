package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.model.Category;
import com.asss.zavrsni.rad.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
}