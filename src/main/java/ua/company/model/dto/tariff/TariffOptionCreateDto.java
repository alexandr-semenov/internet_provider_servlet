package ua.company.model.dto.tariff;

import org.hibernate.validator.constraints.NotEmpty;
import ua.company.model.dto.ValidationDto;

import javax.validation.constraints.NotNull;

public class TariffOptionCreateDto implements ValidationDto {
    @NotNull(message = "tariff_id_empty_error")
    private Long tariffId;

    @NotEmpty(message = "tariff_option_name_empty_error")
    private String option;

    public TariffOptionCreateDto() {
    }

    public Long getTariffId() {
        return tariffId;
    }

    public void setTariffId(Long tariffId) {
        this.tariffId = tariffId;
    }

    public TariffOptionCreateDto(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
