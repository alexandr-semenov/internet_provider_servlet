package ua.company.model.entity;

public class Role {
    private Long id;

    private String name;

    public Role() {
    }

    private Role(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public Role(String name) {
        this.name = name;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
