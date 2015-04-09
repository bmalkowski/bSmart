package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.util.Objects;

public class Account {
    public final Integer id;
    public final String name;
    public final Firm firm;
    public final ImmutableList<Holding> holdings;
    public final ImmutableList<Transaction> transactions;

    private Account(Builder builder) {
        id = builder.id;
        name = builder.name;
        firm = builder.firm;
        holdings = builder.holdings;
        transactions = builder.transactions;
    }

    public BigMoney value() {
        return holdings.stream()
                       .map(Holding::value)
                       .reduce(BigMoney.zero(CurrencyUnit.USD), BigMoney::plus);
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
        final Account other = (Account)obj;
        return Objects.equals(this.id, other.id);
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private Firm firm;
        private ImmutableList<Holding> holdings;
        private ImmutableList<Transaction> transactions;

        public Builder() {
            holdings = ImmutableList.of();
            transactions = ImmutableList.of();
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder firm(Firm firm) {
            this.firm = firm;
            return this;
        }

        public Builder holdings(ImmutableList<Holding> holdings) {
            this.holdings = holdings;
            return this;
        }

        public Builder transactions(ImmutableList<Transaction> transactions) {
            this.transactions = transactions;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
