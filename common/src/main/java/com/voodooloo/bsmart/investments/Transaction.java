package com.voodooloo.bsmart.investments;

import org.joda.money.BigMoney;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    public final Integer id;
    public final Investment investment;
    public final LocalDate tradeDate;
    public final String reason;
    public final BigDecimal quantity;
    public final BigMoney price;

    private Transaction(Builder builder) {
        id = builder.id;
        investment = builder.investment;
        tradeDate = builder.tradeDate;
        reason = builder.reason;
        quantity = builder.quantity;
        price = builder.price;
    }

    public BigMoney value() {
        return price.multipliedBy(quantity);
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
        final Transaction other = (Transaction)obj;
        return Objects.equals(this.id, other.id);
    }

    public static final class Builder {
        private Integer id;
        private Investment investment;
        private LocalDate tradeDate;
        private String reason;
        private BigDecimal quantity;
        private BigMoney price;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder investment(Investment investment) {
            this.investment = investment;
            return this;
        }

        public Builder tradeDate(LocalDate tradeDate) {
            this.tradeDate = tradeDate;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder quantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(BigMoney price) {
            this.price = price;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
