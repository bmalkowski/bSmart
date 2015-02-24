package com.voodooloo.bsmart.investments;

import org.joda.money.Money;

public class Fund {
    public final int id;
    public final String symbol;
    public final String name;
    public final Money price;

    private Fund(Builder builder) {
        id = builder.id;
        symbol = builder.symbol;
        name = builder.name;
        price = builder.price;
    }

    public static final class Builder {
        private String symbol;
        private String name;
        private Money price;
        private int id;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(Money price) {
            this.price = price;
            return this;
        }

        public Fund build() {
            return new Fund(this);
        }
    }
}
