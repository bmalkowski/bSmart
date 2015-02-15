package com.voodooloo.bsmart.investments;

import java.util.Objects;

public class Portfolio {
    public final Integer id;
    public final String name;

    public Portfolio(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Portfolio(Builder builder) {
        id = builder.id;
        name = builder.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Portfolio other = (Portfolio)obj;
        return Objects.equals(this.id, other.id);
    }

    public static final class Builder {
        private Integer id;
        private String name;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Portfolio build() {
            return new Portfolio(this);
        }
    }
}
