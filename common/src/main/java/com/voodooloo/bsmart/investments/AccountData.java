package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.*;
import org.jooq.DSLContext;

import javax.inject.Inject;

import static com.voodooloo.bsmart.generated.Tables.*;

public class AccountData {
    final DSLContext context;
    final EventBus bus;

    @Inject
    public AccountData(DSLContext context, EventBus bus) {
        this.context = context;
        this.bus = bus;
    }

    public void insert(Account account) {
        context.insertInto(ACCOUNTS)
               .set(ACCOUNTS.NAME, account.name)
               .execute();
        bus.post(Event.UPDATED);
    }

    public ImmutableList<Account> findAll() {
        return ImmutableList.copyOf(
                context.selectFrom(ACCOUNTS)
                       .fetch()
                       .stream()
                       .map(this::convertFrom)
                       .iterator()
        );
    }

    Account convertFrom(AccountsRecord record) {
        return new Account.Builder().id(record.getId())
                                    .name(record.getName())
                                    .build();
    }

    public static enum Event {
        UPDATED
    }
}
