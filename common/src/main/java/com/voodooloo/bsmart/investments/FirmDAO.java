package com.voodooloo.bsmart.investments;

import com.voodooloo.bsmart.generated.tables.records.FirmRecord;
import org.jooq.DSLContext;

import static com.voodooloo.bsmart.generated.Tables.FIRM;

public class FirmDAO {
    final DSLContext context;

    public FirmDAO(DSLContext context) {
        this.context = context;
    }

    public Firm find(Integer id) {
        FirmRecord record = context.selectFrom(FIRM).where(FIRM.ID.eq(id)).fetchOne();
        return builderFrom(record).build();
    }

    public Firm.Builder builderFrom(FirmRecord record) {
        return new Firm.Builder().id(record.getId())
                                 .name(record.getName());
    }
}
