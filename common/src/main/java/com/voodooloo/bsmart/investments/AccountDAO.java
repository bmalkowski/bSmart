package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.records.AccountJournalRecord;
import com.voodooloo.bsmart.generated.tables.records.AccountRecord;
import com.voodooloo.bsmart.generated.tables.records.HoldingRecord;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jooq.DSLContext;

import java.sql.Date;
import java.util.ArrayList;

import static com.voodooloo.bsmart.generated.Tables.*;

public class AccountDAO {
    final DSLContext context;
    final EventBus bus;

    public AccountDAO(DSLContext context, EventBus bus) {
        this.context = context;
        this.bus = bus;
    }

    public void insert(Account account) {
        AccountRecord record = context.insertInto(ACCOUNT)
                                      .set(ACCOUNT.NAME, account.name)
                                      .set(ACCOUNT.FIRM_ID, account.firm.id)
                                      .returning(ACCOUNT.ID)
                                      .fetchOne();
        bus.post(find(record.getId()));
    }

    public void add(Account account, Transaction transaction) {
        context.insertInto(ACCOUNT_JOURNAL)
               .set(ACCOUNT_JOURNAL.ACCOUNT_ID, account.id)
               .set(ACCOUNT_JOURNAL.INVESTMENT_ID, transaction.investment.id)
               .set(ACCOUNT_JOURNAL.TRADE_DATE, Date.valueOf(transaction.tradeDate))
               .set(ACCOUNT_JOURNAL.REASON, transaction.reason)
               .set(ACCOUNT_JOURNAL.QUANTITY, transaction.quantity)
               .set(ACCOUNT_JOURNAL.PRICE, transaction.price.getAmount())
               .execute();
        bus.post(find(account.id));
    }

    public Account find(Integer id) {
        InvestmentDAO investmentDAO = new InvestmentDAO(context, bus);
        FirmDAO firmDAO = new FirmDAO(context);
        HoldingDAO holdingDAO = new HoldingDAO();

        ArrayList<Account> accounts = new ArrayList<>();
        context.select()
               .from(ACCOUNT.leftOuterJoin(HOLDING).onKey())
               .where(ACCOUNT.ID.equal(id))
               .fetchGroups(ACCOUNT)
               .forEach((accountRecord, records) -> {
                   ImmutableList.Builder<Holding> holdings = ImmutableList.builder();
                   records.forEach(record -> {
                       HoldingRecord holdingRecord = record.into(HOLDING);
                       if (holdingRecord.getId() == null) {
                           return;
                       }

                       Holding.Builder holdingBuilder = holdingDAO.builderFrom(holdingRecord);
                       holdingBuilder.investment(investmentDAO.find(record.getValue(HOLDING.INVESTMENT_ID)));
                       holdings.add(holdingBuilder.build());
                   });

                   Account.Builder builder = builderFrom(accountRecord);
                   builder.firm(firmDAO.find(accountRecord.getFirmId()));
                   builder.holdings(holdings.build());
                   accounts.add(builder.build());
               });
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    public ImmutableList<Account> findAll() {
        InvestmentDAO investmentDAO = new InvestmentDAO(context, bus);
        FirmDAO firmDAO = new FirmDAO(context);
        HoldingDAO holdingDAO = new HoldingDAO();

        ImmutableList.Builder<Account> accounts = ImmutableList.builder();
        context.select()
               .from(ACCOUNT.leftOuterJoin(HOLDING).onKey())
               .fetchGroups(ACCOUNT)
               .forEach((accountRecord, records) -> {
                   ImmutableList.Builder<Holding> holdings = ImmutableList.builder();
                   records.forEach(record -> {
                       HoldingRecord holdingRecord = record.into(HOLDING);
                       if (holdingRecord.getId() == null) {
                           return;
                       }

                       Holding.Builder holdingBuilder = holdingDAO.builderFrom(holdingRecord);
                       holdingBuilder.investment(investmentDAO.find(record.getValue(HOLDING.INVESTMENT_ID)));
                       holdings.add(holdingBuilder.build());
                   });

                   Account.Builder builder = builderFrom(accountRecord);
                   builder.firm(firmDAO.find(accountRecord.getFirmId()));
                   builder.holdings(holdings.build());
                   accounts.add(builder.build());
               });
        return accounts.build();
    }

    public ImmutableList<Transaction> transactionsFor(Account account) {
        InvestmentDAO investmentDAO = new InvestmentDAO(context, bus);

        ImmutableList.Builder<Transaction> transactions = ImmutableList.builder();
        context.select()
               .from(ACCOUNT_JOURNAL)
               .where(ACCOUNT_JOURNAL.ACCOUNT_ID.eq(account.id))
               .forEach(record -> {
                   AccountJournalRecord accountJournalRecord = record.into(ACCOUNT_JOURNAL);
                   Transaction.Builder builder = builderFrom(accountJournalRecord);
                   builder.investment(investmentDAO.find(record.getValue(ACCOUNT_JOURNAL.INVESTMENT_ID)));
                   transactions.add(builder.build());
               });
        return transactions.build();
    }

    Account.Builder builderFrom(AccountRecord record) {
        return new Account.Builder().id(record.getId())
                                    .name(record.getName());
    }

    Transaction.Builder builderFrom(AccountJournalRecord record) {
        return new Transaction.Builder().id(record.getId())
                                        .tradeDate(record.getTradeDate().toLocalDate())
                                        .reason(record.getReason())
                                        .price(BigMoney.of(CurrencyUnit.USD, record.getPrice()))
                                        .quantity(record.getQuantity());
    }
}
