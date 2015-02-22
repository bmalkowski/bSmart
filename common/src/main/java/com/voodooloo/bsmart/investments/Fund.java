package com.voodooloo.bsmart.investments;

import org.joda.money.Money;

public class Fund {
    public final String symbol;
    public final String name;
    public final Money price;

    private Fund(Builder builder) {
        symbol = builder.symbol;
        name = builder.name;
        price = builder.price;
    }

    public static final class Builder {
        private String symbol;
        private String name;
        private Money price;

        public Builder() {
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
