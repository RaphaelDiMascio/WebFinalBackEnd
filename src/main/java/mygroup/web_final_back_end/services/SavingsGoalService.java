package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.models.SavingsGoal;

import java.util.List;
import java.util.UUID;

public interface SavingsGoalService {
	List<SavingsGoal> getByUserId(UUID userId);
	SavingsGoal create(SavingsGoal goal, UUID userId);
	SavingsGoal updateProgress(UUID goalId, Double newCurrentAmount);
	void deleteById(UUID id);
}
