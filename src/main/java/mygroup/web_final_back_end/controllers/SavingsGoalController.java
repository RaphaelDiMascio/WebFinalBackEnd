package mygroup.web_final_back_end.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mygroup.web_final_back_end.exceptions.SavingsGoalNotFoundByIdException;
import mygroup.web_final_back_end.exceptions.UserNotFoundByIdException;
import mygroup.web_final_back_end.models.SavingsGoal;
import mygroup.web_final_back_end.services.SavingsGoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/savings-goals")
@Tag(name = "Savings Goals Management", description = "Endpoints for managing savings goals")
@CrossOrigin(origins = "*")
public class SavingsGoalController {

	private final SavingsGoalService savingsGoalService;

	public SavingsGoalController(SavingsGoalService savingsGoalService) {
		this.savingsGoalService = savingsGoalService;
	}

	@GetMapping("/user/{userId}")
	@Operation(summary = "Get savings goals by user ID", description = "Retrieve all savings goals belonging to a specific user")
	public ResponseEntity<List<SavingsGoal>> getGoalsByUserId(@PathVariable UUID userId) {
		return ResponseEntity.ok(savingsGoalService.getByUserId(userId));
	}

	@PostMapping
	@Operation(summary = "Create a savings goal", description = "Create a new savings goal for a specific user")
	public ResponseEntity<SavingsGoal> createGoal(
			@RequestBody SavingsGoal goal,
			@RequestParam UUID userId) throws UserNotFoundByIdException {
		return ResponseEntity.ok(savingsGoalService.create(goal, userId));
	}

	@PutMapping("/{id}/progress")
	@Operation(summary = "Update savings goal progress", description = "Update the current saved amount of a specific savings goal")
	public ResponseEntity<SavingsGoal> updateProgress(
			@PathVariable UUID id,
			@RequestParam Double currentAmount) throws SavingsGoalNotFoundByIdException {
		return ResponseEntity.ok(savingsGoalService.updateProgress(id, currentAmount));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete savings goal", description = "Delete a specific savings goal by its unique ID")
	public ResponseEntity<Void> deleteGoal(@PathVariable UUID id) {
		savingsGoalService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
