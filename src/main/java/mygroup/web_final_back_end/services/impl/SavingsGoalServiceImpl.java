package mygroup.web_final_back_end.services.impl;

import mygroup.web_final_back_end.exceptions.BadRequestException;
import mygroup.web_final_back_end.exceptions.SavingsGoalNotFoundByIdException;
import mygroup.web_final_back_end.exceptions.UserNotFoundByIdException;
import mygroup.web_final_back_end.models.SavingsGoal;
import mygroup.web_final_back_end.models.User;
import mygroup.web_final_back_end.models.Transaction;
import mygroup.web_final_back_end.models.TransactionType;
import mygroup.web_final_back_end.repositories.SavingsGoalRepository;
import mygroup.web_final_back_end.repositories.UserRepository;
import mygroup.web_final_back_end.repositories.TransactionRepository;
import mygroup.web_final_back_end.services.SavingsGoalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SavingsGoalServiceImpl implements SavingsGoalService {

	private final SavingsGoalRepository goalRepository;
	private final UserRepository userRepository;
	private final TransactionRepository transactionRepository;

	public SavingsGoalServiceImpl(SavingsGoalRepository goalRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
		this.goalRepository = goalRepository;
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public List<SavingsGoal> getByUserId(UUID userId) {
		return goalRepository.findByUserId(userId);
	}

	@Override
	public SavingsGoal create(SavingsGoal goal, UUID userId) throws UserNotFoundByIdException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundByIdException("User introuvable"));
		goal.setUser(user);
		return goalRepository.save(goal);
	}

	@Override
	public SavingsGoal updateProgress(UUID goalId, Double newCurrentAmount) throws SavingsGoalNotFoundByIdException {
		SavingsGoal goal = goalRepository.findById(goalId)
				.orElseThrow(() -> new SavingsGoalNotFoundByIdException("Objectif introuvable"));

		UUID userId = goal.getUser().getId();
		double oldCurrentAmount = goal.getCurrentAmount() != null ? goal.getCurrentAmount() : 0.0;
		double diff = newCurrentAmount - oldCurrentAmount;

		if (diff > 0) {
			// Enforce check on Compte Courant available balance
			List<Transaction> transactions = transactionRepository.findByUserId(userId);
			double income = 0.0;
			double expense = 0.0;

			for (Transaction tx : transactions) {
				if (tx.getAmount() == null) continue;
				if (tx.getTransactionType() == TransactionType.INCOME) {
					income += tx.getAmount();
				} else if (tx.getTransactionType() == TransactionType.EXPENSE) {
					expense += tx.getAmount();
				}
			}

			double totalSavings = 0.0;
			List<SavingsGoal> goals = goalRepository.findByUserId(userId);
			for (SavingsGoal g : goals) {
				if (g.getCurrentAmount() != null) {
					totalSavings += g.getCurrentAmount();
				}
			}

			double availableBalance = income - expense - totalSavings;
			if (diff > availableBalance) {
				throw new BadRequestException("Solde insuffisant sur votre compte courant pour effectuer ce transfert d'épargne !");
			}
		}

		goal.setCurrentAmount(newCurrentAmount);
		return goalRepository.save(goal);
	}

	@Override
	public SavingsGoal update(UUID id, SavingsGoal goalDetails) throws SavingsGoalNotFoundByIdException {
		SavingsGoal goal = goalRepository.findById(id)
				.orElseThrow(() -> new SavingsGoalNotFoundByIdException("Objectif introuvable"));

		if (goalDetails.getName() == null || goalDetails.getName().trim().isEmpty()) {
			throw new BadRequestException("L'intitulé du projet est obligatoire.");
		}
		if (goalDetails.getAmount() == null || goalDetails.getAmount() <= 0) {
			throw new BadRequestException("Le capital cible doit être supérieur à 0.");
		}
		if (goalDetails.getDeadline() == null) {
			throw new BadRequestException("La date butoir est obligatoire.");
		}

		goal.setName(goalDetails.getName().trim());
		goal.setDescription(goalDetails.getDescription() != null ? goalDetails.getDescription().trim() : null);
		goal.setAmount(goalDetails.getAmount());
		goal.setDeadline(goalDetails.getDeadline());

		return goalRepository.save(goal);
	}

	@Override
	public void deleteById(UUID id) {
		goalRepository.deleteById(id);
	}
}
