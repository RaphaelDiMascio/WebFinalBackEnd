package mygroup.web_final_back_end.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mygroup.web_final_back_end.models.Category;
import mygroup.web_final_back_end.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category Management", description = "Endpoints for managing finance categories")
public class CategoryController {
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	@Operation(summary = "Get all categories", description = "Retrieve all categories with optional search filter on name")
	public ResponseEntity<List<Category>> getAllCategories(@RequestParam(required = false) String name) {
		return ResponseEntity.ok(categoryService.getAll(name));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a new category")
	public ResponseEntity<Category> create(@RequestBody Category category) {
		return ResponseEntity.ok(categoryService.create(category.getName()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
		return ResponseEntity.ok(categoryService.getById(id));
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
		categoryService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
