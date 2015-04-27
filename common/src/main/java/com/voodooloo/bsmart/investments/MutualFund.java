package com.voodooloo.bsmart.investments;

public class MutualFund extends Investment {
    MutualFund(Builder builder) {
        super(builder);
    }

    public static final class Builder extends Investment.Builder {
        public Builder() {}

        public MutualFund build() {
            return new MutualFund(this);
        }
    }
}
