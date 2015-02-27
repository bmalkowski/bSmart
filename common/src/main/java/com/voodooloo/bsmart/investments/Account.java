package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;

public class Account {
    public final Integer id;
    public final String name;
    public final Firm firm;
    public final ImmutableList<FundHolding> fundHoldings;

    private Account(Builder builder) {
        id = builder.id;
        name = builder.name;
        firm = builder.firm;
        fundHoldings = builder.fundHoldings;
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private Firm firm;
        private ImmutableList<FundHolding> fundHoldings;

        public Builder() {
            fundHoldings = ImmutableList.of();
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

        public Builder investments(ImmutableList<FundHolding> fundHoldings) {
            this.fundHoldings = fundHoldings;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
