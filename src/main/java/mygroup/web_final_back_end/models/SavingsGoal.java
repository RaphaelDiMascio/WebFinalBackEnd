package mygroup.web_final_back_end.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "savings_goal")
public class SavingsGoal {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	private String name;

	private String description;

	private Double amount;

	private Double currentAmount = 0.0;

	@Column(name = "deadline")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate deadline;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public SavingsGoal() {
	}

	public SavingsGoal(UUID id, String name, String description, Double amount, Double currentAmount, LocalDate deadline, User user) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.currentAmount = currentAmount;
		this.deadline = deadline;
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

	public Double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
