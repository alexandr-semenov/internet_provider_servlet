package ua.company.model.entity;

import java.util.List;

public class Subscription {
    private Long id;

    private User user;

    private List<Tariff> tariffs;

    private Status status;

    private Double price;

    public Subscription() {
    }

    private Subscription(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.tariffs = builder.tariffs;
        this.status = builder.status;
        this.price = builder.price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status { ACTIVE, NOT_ACTIVE }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private List<Tariff> tariffs;
        private Status status;
        private Double price;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setTariffs(List<Tariff> tariffs) {
            this.tariffs = tariffs;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Subscription build() {
            return new Subscription(this);
        }
    }
}
