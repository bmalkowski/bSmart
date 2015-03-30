package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.*;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jooq.DSLContext;

import javax.inject.Inject;
import java.util.ArrayList;

import static com.voodooloo.bsmart.generated.Tables.*;

public class AccountDAO {
    final DSLContext context;
    final EventBus bus;

    @Inject
    public AccountDAO(DSLContext context, EventBus bus) {
        this.context = context;
        this.bus = bus;
    }

    public void insert(Account account) {
        context.insertInto(ACCOUNT)
               .set(ACCOUNT.NAME, account.name)
               .execute();
    }

    public ImmutableList<Account> findAll() {

        ImmutableList.Builder<Account> accounts = ImmutableList.builder();
        context.select()
               .from(ACCOUNT.join(HOLDING)
                            .onKey())
               .fetchGroups(ACCOUNT)
               .forEach((accountRecord, records) -> {
                   ImmutableList.Builder<Holding> holdings = ImmutableList.builder();
                   records.forEach(record -> {
                       Investment investment = findInvestment(record.getValue(HOLDING.INVESTMENT_ID));

                       HoldingRecord holdingRecord = record.into(HOLDING);
                       Holding.Builder holdingBuilder = builderFrom(holdingRecord);
                       holdingBuilder.fund(investment);
                       holdings.add(holdingBuilder.build());
                   });

                   Account.Builder builder = builderFrom(accountRecord);
                   builder.investments(holdings.build());
                   accounts.add(builder.build());
               });
        return accounts.build();
    }

    Investment findInvestment(Integer id) {
        ArrayList<Investment> investments = new ArrayList<>();
        context.select()
               .from(INVESTMENT.join(INVESTMENT_CATEGORY.join(CATEGORY)
                                                        .onKey())
                               .onKey())
               .where(INVESTMENT.ID.equal(id))
               .fetchGroups(INVESTMENT)
               .forEach((investmentRecord, records) -> {
                   ImmutableList.Builder<PartialCategory> categories = ImmutableList.builder();
                   records.forEach(record -> {
                       CategoryRecord categoryRecord = record.into(CATEGORY);
                       Category.Builder categoryBuilder = builderFrom(categoryRecord);

                       InvestmentCategoryRecord investmentCategoryRecord = record.into(INVESTMENT_CATEGORY);
                       PartialCategory.Builder partialCategoryBuilder = new PartialCategory.Builder()
                               .percentage(investmentCategoryRecord.getPercentage())
                               .category(categoryBuilder.build());

                       categories.add(partialCategoryBuilder.build());
                   });

                   Investment.Builder builder = builderFrom(investmentRecord);
                   builder.partialCategories(categories.build());
                   investments.add(builder.build());
               });

        return investments.isEmpty() ? null : investments.get(0);
    }

    Account.Builder builderFrom(AccountRecord record) {
        return new Account.Builder().id(record.getId())
                                    .name(record.getName());
    }

    Holding.Builder builderFrom(HoldingRecord record) {
        return new Holding.Builder().id(record.getId())
                                    .quantity(record.getQuantity());
    }

    Category.Builder builderFrom(CategoryRecord record) {
        return new Category.Builder().id(record.getId())
                                     .name(record.getName());
    }

    Investment.Builder builderFrom(InvestmentRecord record) {
        return new Investment.Builder().id(record.getId())
                                       .name(record.getName())
                                       .symbol(record.getSymbol())
                                       .price(BigMoney.of(CurrencyUnit.USD, record.getPrice()));
    }
}
