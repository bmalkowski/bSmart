package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

public class Account {
    public final Integer id;
    public final String name;
    public final Firm firm;
    public final ImmutableList<Holding> holdings;

    private Account(Builder builder) {
        id = builder.id;
        name = builder.name;
        firm = builder.firm;
        holdings = builder.holdings;
    }

    public BigMoney value() {
        return holdings.stream()
                       .map(Holding::value)
                       .reduce(BigMoney.zero(CurrencyUnit.USD), BigMoney::plus);
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private Firm firm;
        private ImmutableList<Holding> holdings;

        public Builder() {
            holdings = ImmutableList.of();
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

        public Builder investments(ImmutableList<Holding> holdings) {
            this.holdings = holdings;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
