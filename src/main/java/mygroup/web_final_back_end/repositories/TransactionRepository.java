package mygroup.web_final_back_end.repositories;

import mygroup.web_final_back_end.models.Transaction;
import mygroup.web_final_back_end.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
	List<Transaction> findByUserId(UUID userId);

	@Query("SELECT t FROM Transaction t WHERE " +
			"(:userId IS NULL OR t.user.id = :userId) AND " +
			"(t.transactionDate >= COALESCE(:startDate, t.transactionDate)) AND " +
			"(t.transactionDate <= COALESCE(:endDate, t.transactionDate)) AND " +
			"(:categoryId IS NULL OR t.category.id = :categoryId) AND " +
			"(:type IS NULL OR t.transactionType = :type)")
	List<Transaction> searchTransactions(
			@Param("userId") UUID userId,
			@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate,
			@Param("categoryId") UUID categoryId,
			@Param("type") TransactionType type
	);
}
