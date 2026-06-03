package mygroup.web_final_back_end.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(nullable = false)
	private String name;

	private String description;

	@Column(nullable = false)
	private Double amount;

	@Column(name = "transaction_date", nullable = false)
	// a voir pour suppr
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime transactionDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType transactionType;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Transaction() {}

	public Transaction(String name, String description, Double amount, LocalDateTime transactionDate, TransactionType transactionType, Category category, User user) {
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.category = category;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
