package com.voodooloo.bsmart.investments;

import org.joda.money.BigMoney;

import java.math.BigDecimal;

public class FundHolding {
    public final Integer id;
    public final Account account;
    public final Fund fund;
    public final BigDecimal quantity;

    private FundHolding(Builder builder) {
        id = builder.id;
        account = builder.account;
        fund = builder.fund;
        quantity = builder.quantity;
    }

    public BigMoney value() {
      return fund.price.multipliedBy(quantity);
    }

    public static final class Builder {
        private Integer id;
        private Account account;
        private Fund fund;
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

        public Builder fund(Fund fund) {
            this.fund = fund;
            return this;
        }

        public Builder quantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        public FundHolding build() {
            return new FundHolding(this);
        }
    }
}
