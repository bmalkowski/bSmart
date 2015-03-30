package com.voodooloo.bsmart.investments;

import java.math.BigDecimal;

public class PartialCategory {
  public final Category category;
  public final BigDecimal percentage;

  private PartialCategory(Builder builder) {
    category = builder.category;
    percentage = builder.percentage;
  }

  public static final class Builder {
    private Category category;
    private BigDecimal percentage;

    public Builder() {}

    public Builder category(Category category) {
      this.category = category;
      return this;
    }

    public Builder percentage(BigDecimal percentage) {
      this.percentage = percentage;
      return this;
    }

    public PartialCategory build() { return new PartialCategory(this);}
  }
}
