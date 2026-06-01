package mygroup.web_final_back_end.repositories;

import mygroup.web_final_back_end.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
