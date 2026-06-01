package mygroup.web_final_back_end.services.impl;

import mygroup.web_final_back_end.models.Category;
import mygroup.web_final_back_end.models.Transaction;
import mygroup.web_final_back_end.models.TransactionType;
import mygroup.web_final_back_end.models.User;
import mygroup.web_final_back_end.repositories.CategoryRepository;
import mygroup.web_final_back_end.repositories.TransactionRepository;
import mygroup.web_final_back_end.repositories.UserRepository;
import mygroup.web_final_back_end.services.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Transaction getById(UUID id) {
		return transactionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Transaction introuvable"));
	}

	@Override
	public Transaction create(Transaction transaction, UUID userId, UUID categoryId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

		transaction.setUser(user);
		transaction.setCategory(category);
		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction update(UUID id, Transaction updatedData, UUID categoryId) {
		Transaction existing = getById(id);

		existing.setAmount(updatedData.getAmount());
		existing.setDescription(updatedData.getDescription());
		existing.setTransactionDate(updatedData.getTransactionDate());
		existing.setTransactionType(updatedData.getTransactionType());

		if (categoryId != null) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new RuntimeException("Nouvelle catégorie introuvable"));
			existing.setCategory(category);
		}

		return transactionRepository.save(existing);
	}

	@Override
	public void deleteById(UUID id) {
		transactionRepository.deleteById(id);
	}


	public List<Transaction> search(UUID userId, LocalDate startDate, LocalDate endDate, UUID categoryId, TransactionType type) {
		LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
		LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59, 999999999) : null;
		return transactionRepository.searchTransactions(userId, startDateTime, endDateTime, categoryId, type);
	}
}
