package com.dauphine.web_final_back_end.services.impl;

import jakarta.transaction.Transactional;
import com.dauphine.web_final_back_end.exceptions.BadRequestException;
import com.dauphine.web_final_back_end.exceptions.CategoryNotFoundByIdException;
import com.dauphine.web_final_back_end.models.Category;
import com.dauphine.web_final_back_end.models.User;
import com.dauphine.web_final_back_end.repositories.CategoryRepository;
import com.dauphine.web_final_back_end.repositories.UserRepository;
import com.dauphine.web_final_back_end.repositories.TransactionRepository;
import com.dauphine.web_final_back_end.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;
	private final UserRepository userRepository;
	private final TransactionRepository transactionRepository;

	public CategoryServiceImpl(CategoryRepository repository, UserRepository userRepository, TransactionRepository transactionRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Category create(UUID userId, String name) {
		User user = null;
		if (userId != null) {
			user = userRepository.findById(userId)
					.orElseThrow(() -> new BadRequestException("Utilisateur introuvable avec l'ID: " + userId));
		}

		// Check if a category with this name already exists for this user OR globally
		Optional<Category> existing = repository.findByNameIgnoreCaseAndUserOrGlobal(name, userId);
		if (existing.isPresent()) {
			throw new BadRequestException("La catégorie '" + name + "' existe déjà.");
		}

		Category category = new Category(name);
		category.setUser(user);
		return repository.save(category);
	}

	@Override
	public List<Category> getAll(UUID userId, String name) {
		if (name != null && !name.trim().isEmpty()) {
			String pattern = "%" + name.trim().toLowerCase() + "%";
			return repository.findAllByUserOrGlobalAndName(userId, pattern);
		}
		return repository.findAllByUserOrGlobal(userId);
	}

	@Override
	public Category getById(UUID id) throws CategoryNotFoundByIdException {
		return repository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundByIdException("Catégorie introuvable avec l'ID : " + id));
	}

	@Override
	public void deleteById(UUID id) {
		Category category = repository.findById(id)
				.orElseThrow(() -> new BadRequestException("Catégorie introuvable avec l'ID : " + id));
		if (category.getUser() == null) {
			throw new BadRequestException("Les catégories globales partagées ne peuvent pas être supprimées.");
		}
		if (transactionRepository.existsByCategoryId(id)) {
			throw new BadRequestException("Cette catégorie est utilisée pour une ou plusieurs transactions et ne peut pas être supprimée.");
		}
		repository.delete(category);
	}

	@Override
	public Category update(UUID id, String newName) {
		Category category = repository.findById(id)
				.orElseThrow(() -> new BadRequestException("Catégorie introuvable avec l'ID : " + id));

		if (category.getUser() == null) {
			throw new BadRequestException("Les catégories globales partagées ne peuvent pas être modifiées.");
		}

		if (newName == null || newName.trim().isEmpty()) {
			throw new BadRequestException("Le nom de la catégorie est obligatoire.");
		}

		String trimmedName = newName.trim();

		// check if another category with this name already exists for this user OR globally
		Optional<Category> existing = repository.findByNameIgnoreCaseAndUserOrGlobal(trimmedName, category.getUser().getId());
		if (existing.isPresent() && !existing.get().getId().equals(id)) {
			throw new BadRequestException("La catégorie '" + trimmedName + "' existe déjà.");
		}

		category.setName(trimmedName);
		return repository.save(category);
	}
}

