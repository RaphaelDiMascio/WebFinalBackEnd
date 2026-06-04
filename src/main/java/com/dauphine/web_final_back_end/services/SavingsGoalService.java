package com.dauphine.web_final_back_end.services;

import com.dauphine.web_final_back_end.exceptions.SavingsGoalNotFoundByIdException;
import com.dauphine.web_final_back_end.exceptions.UserNotFoundByIdException;
import com.dauphine.web_final_back_end.models.SavingsGoal;

import java.util.List;
import java.util.UUID;

public interface SavingsGoalService {
	List<SavingsGoal> getByUserId(UUID userId);
	SavingsGoal create(SavingsGoal goal, UUID userId) throws UserNotFoundByIdException;
	SavingsGoal updateProgress(UUID goalId, Double newCurrentAmount) throws SavingsGoalNotFoundByIdException;
	SavingsGoal update(UUID id, SavingsGoal goalDetails) throws SavingsGoalNotFoundByIdException;
	void deleteById(UUID id);
}
