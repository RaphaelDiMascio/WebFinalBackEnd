package mygroup.web_final_back_end.services.impl;

import mygroup.web_final_back_end.models.SavingsGoal;
import mygroup.web_final_back_end.models.User;
import mygroup.web_final_back_end.repositories.SavingsGoalRepository;
import mygroup.web_final_back_end.repositories.UserRepository;
import mygroup.web_final_back_end.services.SavingsGoalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SavingsGoalServiceImpl implements SavingsGoalService {

	private final SavingsGoalRepository goalRepository;
	private final UserRepository userRepository;

	public SavingsGoalServiceImpl(SavingsGoalRepository goalRepository, UserRepository userRepository) {
		this.goalRepository = goalRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<SavingsGoal> getByUserId(UUID userId) {
		return goalRepository.findByUserId(userId);
	}

	@Override
	public SavingsGoal create(SavingsGoal goal, UUID userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User introuvable"));
		goal.setUser(user);
		return goalRepository.save(goal);
	}

	@Override
	public SavingsGoal updateProgress(UUID goalId, Double newCurrentAmount) {
		SavingsGoal goal = goalRepository.findById(goalId)
				.orElseThrow(() -> new RuntimeException("Objectif introuvable"));
		goal.setCurrentAmount(newCurrentAmount);
		return goalRepository.save(goal);
	}

	@Override
	public void deleteById(UUID id) {
		goalRepository.deleteById(id);
	}
}
