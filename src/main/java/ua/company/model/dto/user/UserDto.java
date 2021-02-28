package ua.company.model.dto.user;

import org.hibernate.validator.constraints.NotBlank;

import ua.company.model.dto.ValidationDto;

public class UserDto implements ValidationDto {
    private Long id;

    @NotBlank(message = "username_empty_error")
    String username;

    @NotBlank(message = "password_empty_error")
    String password;

    private boolean active;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
