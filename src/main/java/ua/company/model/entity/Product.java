package ua.company.model.entity;

import javax.persistence.OneToMany;
import java.util.List;

public class Product {
    private Long id;

    private String name;

    @OneToMany(mappedBy = "product")
    private List<Tariff> tariffs;

    public Product() {
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
}
