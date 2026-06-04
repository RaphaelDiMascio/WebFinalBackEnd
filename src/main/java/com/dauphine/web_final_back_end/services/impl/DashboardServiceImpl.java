package com.dauphine.web_final_back_end.services.impl;

import com.dauphine.web_final_back_end.models.Category;
import com.dauphine.web_final_back_end.models.SavingsGoal;
import com.dauphine.web_final_back_end.models.Transaction;
import com.dauphine.web_final_back_end.models.TransactionType;
import com.dauphine.web_final_back_end.repositories.SavingsGoalRepository;
import com.dauphine.web_final_back_end.repositories.TransactionRepository;
import com.dauphine.web_final_back_end.services.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Map<String, Object> getSummary(UUID userId, Integer year, Integer month) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        List<SavingsGoal> goals = savingsGoalRepository.findByUserId(userId);

        // 1. Calculate cumulative balances (all time history)
        double allTimeIncome = 0.0;
        double allTimeExpense = 0.0;
        for (Transaction tx : transactions) {
            if (tx.getAmount() == null) continue;
            if (tx.getTransactionType() == TransactionType.INCOME) {
                allTimeIncome += tx.getAmount();
            } else if (tx.getTransactionType() == TransactionType.EXPENSE) {
                allTimeExpense += tx.getAmount();
            }
        }
        
        double totalSavings = 0.0;
        for (SavingsGoal goal : goals) {
            if (goal.getCurrentAmount() != null) {
                totalSavings += goal.getCurrentAmount();
            }
        }
        double balance = allTimeIncome - allTimeExpense;
        double availableBalance = balance - totalSavings;

        // 2. Identify target month for flow statistics
        int targetYear = year != null ? year : Calendar.getInstance().get(Calendar.YEAR);
        int targetMonth = month != null ? month : (Calendar.getInstance().get(Calendar.MONTH) + 1);

        // If no year/month is provided, default to the latest month containing transactions
        if (year == null && month == null && !transactions.isEmpty()) {
            LocalDateTime maxDate = null;
            for (Transaction tx : transactions) {
                if (tx.getTransactionDate() != null) {
                    if (maxDate == null || tx.getTransactionDate().isAfter(maxDate)) {
                        maxDate = tx.getTransactionDate();
                    }
                }
            }
            if (maxDate != null) {
                targetYear = maxDate.getYear();
                targetMonth = maxDate.getMonthValue();
            }
        }

        // 3. Filter transactions for target month flows and category breakdown
        double monthlyIncome = 0.0;
        double monthlyExpense = 0.0;
        Map<UUID, CategoryAmount> categoryMap = new HashMap<>();

        for (Transaction tx : transactions) {
            if (tx.getAmount() == null || tx.getTransactionDate() == null) continue;
            LocalDateTime txDate = tx.getTransactionDate();
            if (txDate.getYear() == targetYear && txDate.getMonthValue() == targetMonth) {
                if (tx.getTransactionType() == TransactionType.INCOME) {
                    monthlyIncome += tx.getAmount();
                } else if (tx.getTransactionType() == TransactionType.EXPENSE) {
                    monthlyExpense += tx.getAmount();
                    if (tx.getCategory() != null) {
                        Category cat = tx.getCategory();
                        CategoryAmount catAmt = categoryMap.computeIfAbsent(cat.getId(), k -> new CategoryAmount(cat, 0.0));
                        catAmt.amount += tx.getAmount();
                    }
                }
            }
        }

        List<Map<String, Object>> categorySpending = new ArrayList<>();
        for (CategoryAmount catAmt : categoryMap.values()) {
            double percentage = monthlyExpense > 0 ? (catAmt.amount / monthlyExpense) * 100.0 : 0.0;
            Map<String, Object> item = new HashMap<>();
            item.put("category", catAmt.category);
            item.put("amount", catAmt.amount);
            item.put("percentage", percentage);
            categorySpending.add(item);
        }
        
        // Sort descending by amount
        categorySpending.sort((a, b) -> ((Double) b.get("amount")).compareTo((Double) a.get("amount")));

        // 4. Generate monthly specific insights
        List<String> insights = new ArrayList<>();
        if (transactions.isEmpty()) {
            insights.add("Bienvenue ! Ajoutez vos premieres transactions pour recevoir des analyses personnalisees.");
        } else {
            if (monthlyExpense > monthlyIncome) {
                insights.add("Attention : Vos depenses ce mois-ci depassent vos revenus. Pensez a limiter vos achats non essentiels.");
            } else if (monthlyIncome > 0) {
                double savingsRate = ((monthlyIncome - monthlyExpense) / monthlyIncome) * 100.0;
                insights.add(String.format("Bravo ! Vous epargnez actuellement %.0f%% de vos revenus sur ce mois.", savingsRate));
            }

            if (!categorySpending.isEmpty()) {
                Map<String, Object> top = categorySpending.get(0);
                Category cat = (Category) top.get("category");
                insights.add(String.format("Votre poste de depense principal sur ce mois est \"%s\" avec %.0f € (%.0f%% des depenses).",
                        cat.getName(), (Double) top.get("amount"), (Double) top.get("percentage")));
            }

            for (SavingsGoal goal : goals) {
                if (goal.getAmount() != null && goal.getCurrentAmount() != null && goal.getCurrentAmount() < goal.getAmount()) {
                    double percent = (goal.getCurrentAmount() / goal.getAmount()) * 100.0;
                    insights.add(String.format("Votre objectif \"%s\" est complete a %.0f%%. Vous y etes presque !",
                            goal.getName(), percent));
                    break; // Highlight only the first incomplete goal
                }
            }
        }

        // Fetch recent transactions (sorted by date descending, up to 8)
        List<Transaction> sortedTxs = new ArrayList<>(transactions);
        sortedTxs.sort((a, b) -> {
            if (a.getTransactionDate() == null) return 1;
            if (b.getTransactionDate() == null) return -1;
            return b.getTransactionDate().compareTo(a.getTransactionDate());
        });
        List<Transaction> recentTransactions = sortedTxs.subList(0, Math.min(8, sortedTxs.size()));

        // 5. Build dynamic Map summary
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalBalance", balance);
        summary.put("availableBalance", availableBalance);
        summary.put("totalIncome", monthlyIncome);
        summary.put("totalExpense", monthlyExpense);
        summary.put("totalSavings", totalSavings);
        summary.put("categorySpending", categorySpending);
        summary.put("insights", insights);
        summary.put("recentTransactions", recentTransactions);
        summary.put("savingsGoals", goals);
        
        summary.put("activeYear", targetYear);
        summary.put("activeMonth", targetMonth);

        return summary;
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
