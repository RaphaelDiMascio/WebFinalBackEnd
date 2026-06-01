package mygroup.web_final_back_end.controllers;

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
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID id) {
		return ResponseEntity.ok(transactionService.getById(id));
	}

	@PostMapping
	public ResponseEntity<Transaction> createTransaction(
			@RequestBody Transaction transaction,
			@RequestParam UUID userId,
			@RequestParam UUID categoryId) {
		return ResponseEntity.ok(transactionService.create(transaction, userId, categoryId));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Transaction> updateTransaction(
			@PathVariable UUID id,
			@RequestBody Transaction transaction,
			@RequestParam(required = false) UUID categoryId) {
		return ResponseEntity.ok(transactionService.update(id, transaction, categoryId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
		transactionService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
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