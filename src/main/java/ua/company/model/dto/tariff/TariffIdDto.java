package ua.company.model.dto.tariff;

import javax.validation.constraints.NotNull;

public class TariffIdDto {
    @NotNull(message = "tariff_id_empty_error")
    private Long id;

    public TariffIdDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
