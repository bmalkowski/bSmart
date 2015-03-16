package com.voodooloo.bsmart.investments;

import org.joda.money.BigMoney;

import java.math.BigDecimal;

public class PartialHolding {
  public final Holding holding;
  public final BigDecimal percentage;

  private PartialHolding(Builder builder) {
    holding = builder.holding;
    percentage = builder.percentage;
  }

  public BigDecimal quantity() {
    return holding.quantity.multiply(percentage);
  }

  public BigMoney value() {
    return holding.value().multipliedBy(percentage);
  }

  public static final class Builder {
    private Holding holding;
    private BigDecimal percentage;

    public Builder() {}

    public Builder holding(Holding holding) {
      this.holding = holding;
      return this;
    }

    public Builder percentage(BigDecimal percentage) {
      this.percentage = percentage;
      return this;
    }

    public PartialHolding build() { return new PartialHolding(this);}
  }
}
