package ua.company.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AmountDto implements ValidationDto {
    @NotNull(message = "amount_empty_error")
    @Min(value = 0L, message = "amount_negative_error")
    private Double amount;

    public AmountDto() {
    }

    public AmountDto(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
