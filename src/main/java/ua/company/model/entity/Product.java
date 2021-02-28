package ua.company.model.entity;

import java.util.List;

public class Product {
    private Long id;

    private String name;

    private List<Tariff> tariffs;

    public Product() {
    }

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.tariffs = builder.tariffs;
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

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private List<Tariff> tariffs;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTariffs(List<Tariff> tariffs) {
            this.tariffs = tariffs;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
