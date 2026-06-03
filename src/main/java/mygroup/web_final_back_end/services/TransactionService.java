package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.exceptions.CategoryNotFoundByIdException;
import mygroup.web_final_back_end.exceptions.TransactionNotFoundByIdException;
import mygroup.web_final_back_end.exceptions.UserNotFoundByIdException;
import mygroup.web_final_back_end.models.Transaction;
import mygroup.web_final_back_end.models.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
	Transaction getById(UUID id) throws TransactionNotFoundByIdException;

	// on give les id du user et  categorie pour les mettre dans la transaction
	Transaction create(Transaction transaction, UUID userId, UUID categoryId) throws UserNotFoundByIdException, CategoryNotFoundByIdException;
	Transaction update(UUID id, Transaction transaction, UUID categoryId) throws TransactionNotFoundByIdException, CategoryNotFoundByIdException;
	void deleteById(UUID id);
	List<Transaction> search(UUID userId, LocalDate startDate, LocalDate endDate, UUID categoryId, TransactionType type);
}