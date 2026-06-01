package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.models.Category;

import java.util.List;

public interface CategoryService {
	Category create(String name);
	List<Category> getAll(String name);
}
