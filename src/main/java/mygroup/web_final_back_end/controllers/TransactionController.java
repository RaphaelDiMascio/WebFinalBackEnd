package mygroup.web_final_back_end.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mygroup.web_final_back_end.exceptions.CategoryNotFoundByIdException;
import mygroup.web_final_back_end.exceptions.TransactionNotFoundByIdException;
import mygroup.web_final_back_end.exceptions.UserNotFoundByIdException;
import mygroup.web_final_back_end.models.Transaction;
import mygroup.web_final_back_end.models.TransactionType;
import mygroup.web_final_back_end.services.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transactions Management", description = "Endpoints for managing transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get transaction by ID", description = "Retrieve details of a specific transaction by its unique ID")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID id) throws TransactionNotFoundByIdException {
		return ResponseEntity.ok(transactionService.getById(id));
	}

	@PostMapping
	@Operation(summary = "Create a transaction", description = "Create a new transaction (income/expense) for a user and link it to a category")
	public ResponseEntity<Transaction> createTransaction(
			@RequestBody Transaction transaction,
			@RequestParam UUID userId,
			@RequestParam UUID categoryId) throws UserNotFoundByIdException, CategoryNotFoundByIdException {
		return ResponseEntity.ok(transactionService.create(transaction, userId, categoryId));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update transaction details", description = "Modify an existing transaction's details by its ID")
	public ResponseEntity<Transaction> updateTransaction(
			@PathVariable UUID id,
			@RequestBody Transaction transaction,
			@RequestParam(required = false) UUID categoryId) throws TransactionNotFoundByIdException, CategoryNotFoundByIdException {
		return ResponseEntity.ok(transactionService.update(id, transaction, categoryId));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete transaction", description = "Delete a specific transaction by its unique ID")
	public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
		transactionService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	@Operation(summary = "Search transactions", description = "Search and filter user transactions by date range, category, and/or transaction type")
	public ResponseEntity<List<Transaction>> searchTransactions(
			@RequestParam UUID userId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) UUID categoryId,
			@RequestParam(required = false) TransactionType type) {
		List<Transaction> transactions = transactionService.search(userId, startDate, endDate, categoryId, type);
		return ResponseEntity.ok(transactions);
	}
}