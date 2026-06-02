package mygroup.web_final_back_end.services.impl;

import mygroup.web_final_back_end.dto.CategorySpendingDTO;
import mygroup.web_final_back_end.dto.DashboardSummaryDTO;
import mygroup.web_final_back_end.models.Category;
import mygroup.web_final_back_end.models.SavingsGoal;
import mygroup.web_final_back_end.models.Transaction;
import mygroup.web_final_back_end.models.TransactionType;
import mygroup.web_final_back_end.repositories.SavingsGoalRepository;
import mygroup.web_final_back_end.repositories.TransactionRepository;
import mygroup.web_final_back_end.services.DashboardService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final TransactionRepository transactionRepository;
    private final SavingsGoalRepository savingsGoalRepository;

    public DashboardServiceImpl(TransactionRepository transactionRepository, SavingsGoalRepository savingsGoalRepository) {
        this.transactionRepository = transactionRepository;
        this.savingsGoalRepository = savingsGoalRepository;
    }

    @Override
    public DashboardSummaryDTO getSummary(UUID userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        List<SavingsGoal> goals = savingsGoalRepository.findByUserId(userId);

        double income = 0.0;
        double expense = 0.0;
        Map<UUID, CategoryAmount> categoryMap = new HashMap<>();

        for (Transaction tx : transactions) {
            if (tx.getAmount() == null) continue;
            if (tx.getTransactionType() == TransactionType.INCOME) {
                income += tx.getAmount();
            } else if (tx.getTransactionType() == TransactionType.EXPENSE) {
                expense += tx.getAmount();
                if (tx.getCategory() != null) {
                    Category cat = tx.getCategory();
                    CategoryAmount catAmt = categoryMap.computeIfAbsent(cat.getId(), k -> new CategoryAmount(cat, 0.0));
                    catAmt.amount += tx.getAmount();
                }
            }
        }

        List<CategorySpendingDTO> spendingDTOs = new ArrayList<>();
        for (CategoryAmount catAmt : categoryMap.values()) {
            double percentage = expense > 0 ? (catAmt.amount / expense) * 100.0 : 0.0;
            spendingDTOs.add(new CategorySpendingDTO(catAmt.category, catAmt.amount, percentage));
        }

        // Sort descending by amount
        spendingDTOs.sort((a, b) -> b.getAmount().compareTo(a.getAmount()));

        double totalSavings = 0.0;
        for (SavingsGoal goal : goals) {
            if (goal.getCurrentAmount() != null) {
                totalSavings += goal.getCurrentAmount();
            }
        }

        List<String> insights = new ArrayList<>();
        if (transactions.isEmpty()) {
            insights.add("Bienvenue ! Ajoutez vos premières transactions pour recevoir des analyses personnalisées.");
        } else {
            if (expense > income) {
                insights.add("Attention : Vos dépenses mensuelles dépassent vos revenus. Pensez à limiter vos achats non essentiels.");
            } else if (income > 0) {
                double savingsRate = ((income - expense) / income) * 100.0;
                insights.add(String.format("Bravo ! Vous épargnez actuellement %.0f%% de vos revenus ce mois-ci.", savingsRate));
            }

            if (!spendingDTOs.isEmpty()) {
                CategorySpendingDTO top = spendingDTOs.get(0);
                insights.add(String.format("Votre poste de dépense principal est \"%s\" avec un montant cumulé de %.0f € (%.0f%% des dépenses).",
                        top.getCategory().getName(), top.getAmount(), top.getPercentage()));
            }

            for (SavingsGoal goal : goals) {
                if (goal.getAmount() != null && goal.getCurrentAmount() != null && goal.getCurrentAmount() < goal.getAmount()) {
                    double percent = (goal.getCurrentAmount() / goal.getAmount()) * 100.0;
                    insights.add(String.format("Votre objectif \"%s\" est complété à %.0f%%. Vous y êtes presque !",
                            goal.getName(), percent));
                    break; // Highlight only the first incomplete goal
                }
            }
        }

        // Fetch recent transactions (sorted by date descending, up to 6)
        List<Transaction> sortedTxs = new ArrayList<>(transactions);
        sortedTxs.sort((a, b) -> {
            if (a.getTransactionDate() == null) return 1;
            if (b.getTransactionDate() == null) return -1;
            return b.getTransactionDate().compareTo(a.getTransactionDate());
        });
        List<Transaction> recentTransactions = sortedTxs.subList(0, Math.min(6, sortedTxs.size()));

        double balance = income - expense;
        return new DashboardSummaryDTO(balance, income, expense, totalSavings, spendingDTOs, insights, recentTransactions, goals);
    }

    private static class CategoryAmount {
        Category category;
        double amount;

        CategoryAmount(Category category, double amount) {
            this.category = category;
            this.amount = amount;
        }
    }
}
