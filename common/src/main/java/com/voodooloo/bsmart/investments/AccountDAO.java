package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.*;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import javax.inject.Inject;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

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
        Result<Record> records = context.select()
                                      .from(ACCOUNT.join(INVESTMENT).onKey())
                                      .fetch();
        Map<AccountRecord, Result<Record>> map = records.intoGroups(ACCOUNT);
        for (Map.Entry<AccountRecord, Result<Record>> entry : map.entrySet()) {
            ImmutableList.Builder<Investment> investments = ImmutableList.builder();
            Result<InvestmentRecord> investmentRecords = entry.getValue().into(INVESTMENT);
            Stream<Investment> investmentStream = investmentRecords.stream().map(new Function<InvestmentRecord, Investment>() {
                @Override
                public Investment apply(InvestmentRecord investmentRecord) {
                    return new Investment.Builder().id(investmentRecord.getId())
                                                   .quantity(investmentRecord.getQuantity())
                                                   .build();
                }
            });
            investments.addAll(investmentStream.iterator());

            Account.Builder builder = builderFrom(entry.getKey());
            builder.investments(investments.build());
            accounts.add(builder.build());
        }

        return accounts.build();
//        return ImmutableList.copyOf(
//                context.selectFrom(ACCOUNT)
//                       .fetch()
//                       .stream()
//                       .map(this::convertFrom)
//                       .iterator()
//                                   );

    }

    Account.Builder builderFrom(AccountRecord record) {
        return new Account.Builder().id(record.getId())
                                    .name(record.getName());
    }

    public static enum Event {
        UPDATED
    }
}
