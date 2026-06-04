package com.dauphine.web_final_back_end.services;

import com.dauphine.web_final_back_end.exceptions.CategoryNotFoundByIdException;
import com.dauphine.web_final_back_end.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
	Category create(UUID userId, String name);
	List<Category> getAll(UUID userId, String name);
	Category getById(UUID id) throws CategoryNotFoundByIdException;
	void deleteById(UUID id);
}
