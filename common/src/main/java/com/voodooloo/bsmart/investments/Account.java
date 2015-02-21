package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;

public class Account {
    public final Integer id;
    public final String name;

    //firm
    //type (ira, taxable, etc)

    public final ImmutableList<Fund> funds;

    private Account(Builder builder) {
        id = builder.id;
        name = builder.name;
        funds = builder.funds;
    }

    public static final class Builder {
        private Integer id;
        private String name;
        private ImmutableList<Fund> funds;

        public Builder() {
            funds = ImmutableList.of();
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder funds(ImmutableList<Fund> funds) {
            this.funds = funds;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
