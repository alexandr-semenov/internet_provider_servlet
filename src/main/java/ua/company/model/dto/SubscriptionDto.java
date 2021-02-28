package ua.company.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import ua.company.model.dto.tariff.TariffIdDto;

import java.util.List;

public class SubscriptionDto implements ValidationDto {
    private int productId;

    private List<TariffIdDto> tariffs;

    @NotBlank(message = "username_empty_error")
    private String username;

    @NotBlank(message = "password_empty_error")
    private String password;

    public SubscriptionDto() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public List<TariffIdDto> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<TariffIdDto> tariffs) {
        this.tariffs = tariffs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
