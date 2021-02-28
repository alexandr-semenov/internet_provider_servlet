package ua.company.model.dto.tariff;

import org.hibernate.validator.constraints.NotEmpty;

import ua.company.model.dto.ValidationDto;

import javax.validation.constraints.NotNull;

public class TariffOptionUpdateDto implements ValidationDto {
    @NotNull(message = "tariff_id_empty_error")
    private Long id;

    @NotEmpty(message = "tariff_option_name_empty_error")
    private String option;

    public TariffOptionUpdateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TariffOptionUpdateDto(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
