package mygroup.web_final_back_end.dto;

import mygroup.web_final_back_end.models.Category;

public class CategorySpendingDTO {
    private Category category;
    private Double amount;
    private Double percentage;

    public CategorySpendingDTO() {}

    public CategorySpendingDTO(Category category, Double amount, Double percentage) {
        this.category = category;
        this.amount = amount;
        this.percentage = percentage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
