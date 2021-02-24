package ua.company.model.dto.tariff;

import org.hibernate.validator.constraints.NotEmpty;

public class TariffOptionDto {
    @NotEmpty(message = "tariff_option_name_empty_error")
    private String option;

    public TariffOptionDto() {
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
