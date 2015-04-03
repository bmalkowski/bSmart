package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.AccountRecord;
import com.voodooloo.bsmart.generated.tables.records.HoldingRecord;
import org.jooq.DSLContext;

import static com.voodooloo.bsmart.generated.Tables.ACCOUNT;
import static com.voodooloo.bsmart.generated.Tables.HOLDING;

public class AccountDAO {
    final DSLContext context;
    final EventBus bus;

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
        InvestmentDAO investmentDAO = new InvestmentDAO(context);
        FirmDAO firmDAO = new FirmDAO(context);
        HoldingDAO holdingDAO = new HoldingDAO();

        ImmutableList.Builder<Account> accounts = ImmutableList.builder();
        context.select()
               .from(ACCOUNT.join(HOLDING).onKey())
               .fetchGroups(ACCOUNT)
               .forEach((accountRecord, records) -> {
                   ImmutableList.Builder<Holding> holdings = ImmutableList.builder();
                   records.forEach(record -> {
                       Investment investment = investmentDAO.find(record.getValue(HOLDING.INVESTMENT_ID));

                       HoldingRecord holdingRecord = record.into(HOLDING);
                       Holding.Builder holdingBuilder = holdingDAO.builderFrom(holdingRecord);
                       holdingBuilder.investment(investment);
                       holdings.add(holdingBuilder.build());
                   });

                   Account.Builder builder = builderFrom(accountRecord);
                   builder.firm(firmDAO.find(accountRecord.getFirmId()));
                   builder.investments(holdings.build());
                   accounts.add(builder.build());
               });
        return accounts.build();
    }

    Account.Builder builderFrom(AccountRecord record) {
        return new Account.Builder().id(record.getId())
                                    .name(record.getName());
    }
}
