package ua.company.model.entity;

import java.util.List;

public class Tariff {
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Product product;

    private List<TariffOption> options;

    public Tariff() {
    }

    private Tariff(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.product = builder.product;
        this.options = builder.options;
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

    public List<TariffOption> getOptions() {
        return options;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOptions(List<TariffOption> options) {
        this.options = options;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private Product product;
        private List<TariffOption> options;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setOptions(List<TariffOption> options) {
            this.options = options;
            return this;
        }

        public Tariff build() {
            return new Tariff(this);
        }
    }
}
