package com.voodooloo.bsmart.investments;

import org.joda.money.BigMoney;

import java.math.BigDecimal;

public class Holding {
    public final Integer id;
    public final Account account;
    public final Investment investment;
    public final BigDecimal quantity;

    private Holding(Builder builder) {
        id = builder.id;
        account = builder.account;
        investment = builder.investment;
        quantity = builder.quantity;
    }

    public BigMoney value() {
      return investment.price.multipliedBy(quantity);
    }

    public static final class Builder {
        private Integer id;
        private Account account;
        private Investment investment;
        private BigDecimal quantity;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder investment(Investment investment) {
            this.investment = investment;
            return this;
        }

        public Builder quantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        public Holding build() {
            return new Holding(this);
        }
    }
}
