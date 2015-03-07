package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.AccountRecord;
import com.voodooloo.bsmart.generated.tables.records.HoldingRecord;
import com.voodooloo.bsmart.generated.tables.records.InvestmentRecord;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jooq.DSLContext;

import javax.inject.Inject;

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
        bus.post(Event.UPDATED);
    }

    public ImmutableList<Account> findAll() {
        ImmutableList.Builder<Account> accounts = ImmutableList.builder();
        context.select()
               .from(ACCOUNT.join(HOLDING.join(INVESTMENT)
                                         .onKey())
                            .onKey())
               .fetchGroups(ACCOUNT)
               .forEach((accountRecord, records) -> {
                   ImmutableList.Builder<Holding> holdings = ImmutableList.builder();
                   records.forEach(record -> {
                       InvestmentRecord investmentRecord = record.into(INVESTMENT);
                       Investment.Builder investmentBuilder = builderFrom(investmentRecord);

                       HoldingRecord holdingRecord = record.into(HOLDING);
                       Holding.Builder holdingBuilder = builderFrom(holdingRecord);
                       holdingBuilder.fund(investmentBuilder.build());
                       holdings.add(holdingBuilder.build());
                   });

                   Account.Builder builder = builderFrom(accountRecord);
                   builder.investments(holdings.build());
                   accounts.add(builder.build());
               });
        return accounts.build();
    }

    Account.Builder builderFrom(AccountRecord record) {
        return new Account.Builder().id(record.getId())
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

    public static enum Event {
        UPDATED
    }
}
