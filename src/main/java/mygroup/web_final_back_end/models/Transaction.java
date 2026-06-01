package mygroup.web_final_back_end.models;


import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
}
