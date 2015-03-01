package com.voodooloo.bsmart.triggers;

import org.h2.api.Trigger;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static com.voodooloo.bsmart.generated.Tables.INVESTMENT;
import static com.voodooloo.bsmart.generated.Tables.JOURNAL;
import static org.jooq.impl.DSL.sum;

public class JournalUpdate implements Trigger {
    @Override
    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) throws SQLException {

    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        DSLContext context = DSL.using(conn, SQLDialect.H2);

        Object[] existingRow = newRow == null ? oldRow : newRow;
        Integer accountId = (Integer)existingRow[1];
        Integer fundId = (Integer)existingRow[2];
        Record1<BigDecimal> sumRecord = context.select(sum(JOURNAL.DELTA))
                                               .from(JOURNAL)
                                               .where(JOURNAL.ACCOUNT_ID.equal(accountId))
                                               .fetchOne();

        context.mergeInto(INVESTMENT,
                          INVESTMENT.ACCOUNT_ID,
                          INVESTMENT.FUND_ID,
                          INVESTMENT.QUANTITY)
               .key(INVESTMENT.ACCOUNT_ID, INVESTMENT.FUND_ID)
               .values(accountId, fundId, sumRecord.value1())
               .execute();
    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public void remove() throws SQLException {

    }
}
