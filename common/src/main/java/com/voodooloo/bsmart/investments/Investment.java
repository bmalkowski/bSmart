package com.voodooloo.bsmart.investments;

import java.math.BigDecimal;

public class Investment {
    public final Integer id;
    public final Account account;
    public final Fund fund;
    public final BigDecimal quantity;

    private Investment(Builder builder) {
        id = builder.id;
        account = builder.account;
        fund = builder.fund;
        quantity = builder.quantity;
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

        public Investment build() {
            return new Investment(this);
        }
    }
}
