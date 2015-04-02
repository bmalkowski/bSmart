package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.*;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jooq.DSLContext;

import javax.inject.Inject;

import static com.voodooloo.bsmart.generated.Tables.*;

public class PortfolioDAO {
  final DSLContext context;
  final EventBus bus;

  @Inject
  public PortfolioDAO(DSLContext context, EventBus bus) {
    this.context = context;
    this.bus = bus;
  }

  public void insert(Portfolio portfolio) {
    context.insertInto(PORTFOLIO)
           .set(PORTFOLIO.NAME, portfolio.name)
           .execute();
  }

  public ImmutableList<Portfolio> findAll() {
    ImmutableList.Builder<Portfolio> portfolios = ImmutableList.builder();
    context.select()
           .from(PORTFOLIO.join(PORTFOLIO_HOLDING.join(HOLDING.join(INVESTMENT)
                                                              .onKey())
                                                 .on(PORTFOLIO_HOLDING.HOLDING_ID.equal(HOLDING.ID)))
                          .onKey())
           .fetchGroups(PORTFOLIO)
           .forEach((portfolioRecord, records) -> {
             ImmutableList.Builder<PartialHolding> holdings = ImmutableList.builder();
             records.forEach(record -> {
               InvestmentRecord investmentRecord = record.into(INVESTMENT);
               Investment.Builder investmentBuilder = builderFrom(investmentRecord);

               HoldingRecord holdingRecord = record.into(HOLDING);
               Holding.Builder holdingBuilder = builderFrom(holdingRecord);
               holdingBuilder.investment(investmentBuilder.build());

               PortfolioHoldingRecord portfolioHoldingRecord = record.into(PORTFOLIO_HOLDING);
               PartialHolding.Builder partialHoldingBuilder = new PartialHolding.Builder()
                   .percentage(portfolioHoldingRecord.getPercentage())
                   .holding(holdingBuilder.build());

               holdings.add(partialHoldingBuilder.build());
             });

             Portfolio.Builder builder = builderFrom(portfolioRecord);
             builder.partialHoldings(holdings.build());
             portfolios.add(builder.build());
           });
    return portfolios.build();
  }

  Portfolio.Builder builderFrom(PortfolioRecord record) {
    return new Portfolio.Builder().id(record.getId())
                                .name(record.getName());
  }

  Holding.Builder builderFrom(HoldingRecord record) {
    return new Holding.Builder().id(record.getId())
                                .quantity(record.getQuantity());
  }

  Investment.Builder builderFrom(InvestmentRecord record) {
    return new Investment.Builder().id(record.getId())
                                   .name(record.getName())
                                   .symbol(record.getSymbol())
                                   .price(BigMoney.of(CurrencyUnit.USD, record.getPrice()));
  }
}
