package ua.company.model.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UserDto implements ValidationDto {
    @NotBlank(message = "username_empty_error")
    String username;

    @NotBlank(message = "password_empty_error")
    String password;

    public UserDto() {
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
