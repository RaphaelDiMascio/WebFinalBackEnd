package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.models.Category;

import java.util.List;

public interface CategoryService {
	Category create(Category category);
	List<Category> getAll();
}
