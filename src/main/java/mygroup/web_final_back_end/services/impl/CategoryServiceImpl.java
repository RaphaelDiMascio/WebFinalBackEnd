package mygroup.web_final_back_end.services.impl;

import jakarta.transaction.Transactional;
import mygroup.web_final_back_end.exceptions.BadRequestException;
import mygroup.web_final_back_end.exceptions.CategoryNotFoundByIdException;
import mygroup.web_final_back_end.models.Category;
import mygroup.web_final_back_end.repositories.CategoryRepository;
import mygroup.web_final_back_end.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;

	public CategoryServiceImpl(CategoryRepository repository) {
		this.repository = repository;
	}

	public Category create(String name) {
		if (repository.findByName(name).isPresent()) {
			throw new BadRequestException("La catégorie '" + name + "' existe déjà.");
		}
		Category category = new Category(name);
		return repository.save(category);
	}

	@Override
	public List<Category> getAll(String name) {
		if (name != null && !name.trim().isEmpty()) {
			return repository.findByNameContainingIgnoreCase(name);
		}
		return repository.findAll();
	}

	public Category getById(UUID id) throws CategoryNotFoundByIdException {
		return repository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundByIdException("Catégorie introuvable avec l'ID : " + id));
	}

	@Override
	public void deleteById(UUID id) {
		repository.deleteById(id);
	}
}
