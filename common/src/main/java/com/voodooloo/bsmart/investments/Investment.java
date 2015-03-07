package com.voodooloo.bsmart.investments;

import org.joda.money.BigMoney;

public class Investment {
    public final int id;
    public final String symbol;
    public final String name;
    public final BigMoney price;

    private Investment(Builder builder) {
        id = builder.id;
        symbol = builder.symbol;
        name = builder.name;
        price = builder.price;
    }

    public static final class Builder {
        private String symbol;
        private String name;
        private BigMoney price;
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

        public Builder price(BigMoney price) {
            this.price = price;
            return this;
        }

        public Investment build() {
            return new Investment(this);
        }
    }
}
