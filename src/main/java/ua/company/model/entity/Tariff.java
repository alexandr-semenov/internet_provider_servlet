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
}
