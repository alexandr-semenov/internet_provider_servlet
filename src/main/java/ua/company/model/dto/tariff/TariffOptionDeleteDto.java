package ua.company.model.dto.tariff;

import ua.company.model.dto.ValidationDto;

import javax.validation.constraints.NotNull;

public class TariffOptionDeleteDto implements ValidationDto {
    @NotNull(message = "tariff_id_empty_error")
    private Long id;

    public TariffOptionDeleteDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
