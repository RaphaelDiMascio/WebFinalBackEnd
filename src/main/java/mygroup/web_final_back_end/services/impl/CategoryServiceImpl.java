package mygroup.web_final_back_end.services.impl;

import jakarta.transaction.Transactional;
import mygroup.web_final_back_end.models.Category;
import mygroup.web_final_back_end.repositories.CategoryRepository;
import mygroup.web_final_back_end.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;

	public CategoryServiceImpl(CategoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public Category create(Category category) {
		return repository.save(category);
	}

	@Override
	public List<Category> getAll() {
		return repository.findAll();
	}
}
