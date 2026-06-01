package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.models.Transaction;
import mygroup.web_final_back_end.models.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
	Transaction getById(UUID id);

	// on give les id du user et  categorie pour les mettre dans la transaction
	Transaction create(Transaction transaction, UUID userId, UUID categoryId);
	Transaction update(UUID id, Transaction transaction, UUID categoryId);
	void deleteById(UUID id);
	List<Transaction> search(UUID userId, LocalDate startDate, LocalDate endDate, UUID categoryId, TransactionType type);
}