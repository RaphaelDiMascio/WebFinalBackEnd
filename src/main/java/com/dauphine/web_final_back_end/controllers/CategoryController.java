package com.dauphine.web_final_back_end.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.dauphine.web_final_back_end.exceptions.CategoryNotFoundByIdException;
import com.dauphine.web_final_back_end.models.Category;
import com.dauphine.web_final_back_end.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category Management", description = "Endpoints for managing finance categories")
@CrossOrigin(origins = "*")
public class CategoryController {
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	@Operation(summary = "Get all categories", description = "Retrieve all categories with optional search filter on name and scoped to a specific user (including global categories)")
	public ResponseEntity<List<Category>> getAllCategories(
			@RequestParam(required = false) UUID userId,
			@RequestParam(required = false) String name) {
		return ResponseEntity.ok(categoryService.getAll(userId, name));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a new category")
	public ResponseEntity<Category> create(
			@RequestParam(required = false) UUID userId,
			@RequestBody Category category) {
		return ResponseEntity.ok(categoryService.create(userId, category.getName()));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get category by ID", description = "Retrieve a specific category by its unique ID")
	public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) throws CategoryNotFoundByIdException {
		return ResponseEntity.ok(categoryService.getById(id));
	}


	@DeleteMapping("/{id}")
	@Operation(summary = "Delete category", description = "Delete a category by its unique ID")
	public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
		categoryService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
