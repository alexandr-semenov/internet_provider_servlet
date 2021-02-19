package ua.company.model.dto.user;

import ua.company.model.dto.ValidationDto;

import javax.validation.constraints.NotNull;

public class UserIdDto implements ValidationDto {
    @NotNull(message = "id empty error")
    private Long id;

    public UserIdDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
