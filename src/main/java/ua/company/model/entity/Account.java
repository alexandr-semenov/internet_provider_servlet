package ua.company.model.entity;

public class Account {
    private Long id;

    private Double amount;

    private User user;

    public Account() {
    }

    private Account(Builder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.user = builder.user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Double amount;
        private User user;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
