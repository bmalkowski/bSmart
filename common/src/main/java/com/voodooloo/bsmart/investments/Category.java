package com.voodooloo.bsmart.investments;

public class Category {
    public final int id;
    public final String name;

    private Category(Builder builder) {
        id = builder.id;
        name = builder.name;
    }

    public static final class Builder {
        private int id;
        private String name;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
