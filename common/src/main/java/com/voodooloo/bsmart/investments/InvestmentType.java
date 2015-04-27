package com.voodooloo.bsmart.investments;

public class InvestmentType {
    public enum T {
        MUTUAL_FUND,
    }

    public final int id;
    public final String name;
    public final T t;

    private InvestmentType(Builder builder) {
        id = builder.id;
        name = builder.name;
        t = T.MUTUAL_FUND;
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

        public InvestmentType build() {
            return new InvestmentType(this);
        }
    }
}
