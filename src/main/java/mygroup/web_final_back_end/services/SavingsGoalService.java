package mygroup.web_final_back_end.services;

import mygroup.web_final_back_end.exceptions.SavingsGoalNotFoundByIdException;
import mygroup.web_final_back_end.exceptions.UserNotFoundByIdException;
import mygroup.web_final_back_end.models.SavingsGoal;

import java.util.List;
import java.util.UUID;

public interface SavingsGoalService {
	List<SavingsGoal> getByUserId(UUID userId);
	SavingsGoal create(SavingsGoal goal, UUID userId) throws UserNotFoundByIdException;
	SavingsGoal updateProgress(UUID goalId, Double newCurrentAmount) throws SavingsGoalNotFoundByIdException;
	void deleteById(UUID id);
}
