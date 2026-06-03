package mygroup.web_final_back_end.controllers;
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
	public ResponseEntity<List<SavingsGoal>> getGoalsByUserId(@PathVariable UUID userId) {
		return ResponseEntity.ok(savingsGoalService.getByUserId(userId));
	}

	@PostMapping
	public ResponseEntity<SavingsGoal> createGoal(
			@RequestBody SavingsGoal goal,
			@RequestParam UUID userId) throws UserNotFoundByIdException {
		return ResponseEntity.ok(savingsGoalService.create(goal, userId));
	}

	@PutMapping("/{id}/progress")
	public ResponseEntity<SavingsGoal> updateProgress(
			@PathVariable UUID id,
			@RequestParam Double currentAmount) throws SavingsGoalNotFoundByIdException {
		return ResponseEntity.ok(savingsGoalService.updateProgress(id, currentAmount));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGoal(@PathVariable UUID id) {
		savingsGoalService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
