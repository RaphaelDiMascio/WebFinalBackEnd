package mygroup.web_final_back_end.dto;

import mygroup.web_final_back_end.models.Transaction;
import mygroup.web_final_back_end.models.SavingsGoal;
import java.util.List;

public class DashboardSummaryDTO {
    private Double totalBalance;
    private Double availableBalance;
    private Double totalIncome;
    private Double totalExpense;
    private Double totalSavings;
    private List<CategorySpendingDTO> categorySpending;
    private List<String> insights;
    private List<Transaction> recentTransactions;
    private List<SavingsGoal> savingsGoals;

    public DashboardSummaryDTO() {}

    public DashboardSummaryDTO(Double totalBalance, Double availableBalance, Double totalIncome, Double totalExpense, Double totalSavings,
                               List<CategorySpendingDTO> categorySpending, List<String> insights,
                               List<Transaction> recentTransactions, List<SavingsGoal> savingsGoals) {
        this.totalBalance = totalBalance;
        this.availableBalance = availableBalance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.totalSavings = totalSavings;
        this.categorySpending = categorySpending;
        this.insights = insights;
        this.recentTransactions = recentTransactions;
        this.savingsGoals = savingsGoals;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Double getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(Double totalSavings) {
        this.totalSavings = totalSavings;
    }

    public List<CategorySpendingDTO> getCategorySpending() {
        return categorySpending;
    }

    public void setCategorySpending(List<CategorySpendingDTO> categorySpending) {
        this.categorySpending = categorySpending;
    }

    public List<String> getInsights() {
        return insights;
    }

    public void setInsights(List<String> insights) {
        this.insights = insights;
    }

    public List<Transaction> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<Transaction> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }

    public List<SavingsGoal> getSavingsGoals() {
        return savingsGoals;
    }

    public void setSavingsGoals(List<SavingsGoal> savingsGoals) {
        this.savingsGoals = savingsGoals;
    }
}
