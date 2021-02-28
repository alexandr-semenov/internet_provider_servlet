package ua.company.model.entity;

public class User {
    private Long id;

    private String username;

    private String password;

    private boolean active;

    private Role role;

    private Subscription subscription;

    private Account account;

    public User() {
    }

    public User(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

    private User(Builder builder) {
        this.id= builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.active = builder.active;
        this.role = builder.role;
        this.subscription = builder.subscription;
        this.account = builder.account;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private boolean active;
        private Role role;
        private Subscription subscription;
        private Account account;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder setSubscription(Subscription subscription) {
            this.subscription = subscription;
            return this;
        }

        public Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
