package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.*;
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
        context.insertInto(PORTFOLIOS)
               .set(PORTFOLIOS.NAME, portfolio.name)
               .execute();
        bus.post(Event.UPDATED);
    }

    public ImmutableList<Portfolio> findAll() {
        return ImmutableList.copyOf(
                context.selectFrom(PORTFOLIOS)
                       .fetch()
                       .stream()
                       .map(this::convertFrom)
                       .iterator()
        );
    }

    Portfolio convertFrom(PortfoliosRecord record) {
        return new Portfolio.Builder().id(record.getId())
                                      .name(record.getName())
                                      .build();
    }

    public static enum Event {
        UPDATED
    }
}
