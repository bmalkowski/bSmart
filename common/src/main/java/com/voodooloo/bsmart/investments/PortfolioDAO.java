package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.HoldingRecord;
import com.voodooloo.bsmart.generated.tables.records.PortfolioHoldingRecord;
import com.voodooloo.bsmart.generated.tables.records.PortfolioRecord;
import org.jooq.DSLContext;

import static com.voodooloo.bsmart.generated.Tables.*;

public class PortfolioDAO {
    final DSLContext context;
    final EventBus bus;

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
        InvestmentDAO investmentDAO = new InvestmentDAO(context, bus);
        HoldingDAO holdingDAO = new HoldingDAO();
        AccountDAO accountDAO = new AccountDAO(context, bus);

        ImmutableList.Builder<Portfolio> portfolios = ImmutableList.builder();
        context.select()
               .from(PORTFOLIO.join(PORTFOLIO_HOLDING.join(HOLDING)
                                                     .onKey())
                              .onKey())
               .fetchGroups(PORTFOLIO)
               .forEach((portfolioRecord, records) -> {
                   ImmutableList.Builder<PartialHolding> holdings = ImmutableList.builder();
                   records.forEach(record -> {
                       Investment investment = investmentDAO.find(record.getValue(HOLDING.INVESTMENT_ID));

                       HoldingRecord holdingRecord = record.into(HOLDING);
                       Holding.Builder holdingBuilder = holdingDAO.builderFrom(holdingRecord);
                       holdingBuilder.investment(investment);
                       holdingBuilder.account(accountDAO.find(holdingRecord.getAccountId()));

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
}
