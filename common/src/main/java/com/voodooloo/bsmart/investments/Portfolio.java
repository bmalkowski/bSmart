package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.util.Objects;

public class Portfolio {
  public final Integer id;
  public final String name;
  public final ImmutableList<PartialHolding> partialHoldings;

  private Portfolio(Builder builder) {
    id = builder.id;
    name = builder.name;
    partialHoldings = builder.partialHoldings;
  }

  public BigMoney value() {
    return partialHoldings.stream()
                          .map(PartialHolding::value)
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
    final Portfolio other = (Portfolio)obj;
    return Objects.equals(this.id, other.id);
  }

  public static final class Builder {
    private Integer id;
    private String name;
    private ImmutableList<PartialHolding> partialHoldings;

    public Builder() {
      partialHoldings = ImmutableList.of();
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder partialHoldings(ImmutableList<PartialHolding> partialHoldings) {
      this.partialHoldings = partialHoldings;
      return this;
    }

    public Portfolio build() {
      return new Portfolio(this);
    }
  }
}
