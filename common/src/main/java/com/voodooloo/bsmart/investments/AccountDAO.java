package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.AccountRecord;
import com.voodooloo.bsmart.generated.tables.records.FundRecord;
import com.voodooloo.bsmart.generated.tables.records.InvestmentRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import javax.inject.Inject;
import java.util.Map;

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
        Map<AccountRecord, Result<Record>> records = context.select()
                                                            .from(ACCOUNT.join(INVESTMENT.join(FUND)
                                                                                         .onKey())
                                                                         .onKey())
                                                            .fetchGroups(ACCOUNT);

        ImmutableList.Builder<Account> accounts = ImmutableList.builder();
        for (Map.Entry<AccountRecord, Result<Record>> entry : records.entrySet()) {
            ImmutableList.Builder<Investment> investments = ImmutableList.builder();

            for (Record record : entry.getValue()) {
                FundRecord fundRecord = record.into(FUND);
                Fund.Builder builder = builderFrom(fundRecord);

                InvestmentRecord investmentRecord = record.into(INVESTMENT);
                Investment.Builder investmentBuilder = builderFrom(investmentRecord);
                investmentBuilder.fund(builder.build());
                investments.add(investmentBuilder.build());
            }

            Account.Builder builder = builderFrom(entry.getKey());
            builder.investments(investments.build());
            accounts.add(builder.build());
        }

        return accounts.build();
    }

    Account.Builder builderFrom(AccountRecord record) {
        return new Account.Builder().id(record.getId())
                                    .name(record.getName());
    }

    Investment.Builder builderFrom(InvestmentRecord record) {
        return new Investment.Builder().id(record.getId())
                                       .quantity(record.getQuantity());
    }

    Fund.Builder builderFrom(FundRecord record) {
        return new Fund.Builder().id(record.getId())
                                 .name(record.getName());
    }

    public static enum Event {
        UPDATED
    }
}
