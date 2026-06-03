package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.exceptions.CategoryNotFoundByIdException;
import mygroup.web_final_back_end.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
	Category create(String name);
	List<Category> getAll(String name);
	Category getById(UUID id) throws CategoryNotFoundByIdException;
	void deleteById(UUID id);

}
