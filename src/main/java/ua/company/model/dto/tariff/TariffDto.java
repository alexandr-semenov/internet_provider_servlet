package ua.company.model.dto.tariff;

import org.hibernate.validator.constraints.NotEmpty;
import ua.company.model.dto.ValidationDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TariffDto implements ValidationDto {
    private Long id;

    @NotEmpty(message = "tariff_name_empty_error")
    private String name;

    @NotEmpty(message = "tariff_description_empty_error")
    private String description;

    @NotNull(message = "tariff_price_negative_error")
    @Min(value = 0L, message = "amount_negative_error")
    private Double price;

    private Long productId;

    private List<TariffOptionCreateDto> tariffOption;

    public TariffDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<TariffOptionCreateDto> getTariffOption() {
        return tariffOption;
    }

    public void setTariffOption(List<TariffOptionCreateDto> tariffOption) {
        this.tariffOption = tariffOption;
    }
}
