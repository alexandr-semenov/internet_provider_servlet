package ua.company.model.entity;

public class TariffOption {
    private Long id;

    private String item;

    private Tariff tariff;

    public TariffOption() {
    }

    private TariffOption(Builder builder) {
        this.id = builder.id;
        this.item = builder.item;
        this.tariff = builder.tariff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String item;
        private Tariff tariff;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setItem(String item) {
            this.item = item;
            return this;
        }

        public Builder setTariff(Tariff tariff) {
            this.tariff = tariff;
            return this;
        }

        public TariffOption build() {
            return new TariffOption(this);
        }
    }
}
