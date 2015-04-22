package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
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

    public ImmutableList<Firm> findAll() {
        ImmutableList.Builder<Firm> firms = ImmutableList.builder();
        context.selectFrom(FIRM).forEach(firmRecord -> firms.add(builderFrom(firmRecord).build()));
        return firms.build();
    }

    public Firm.Builder builderFrom(FirmRecord record) {
        return new Firm.Builder().id(record.getId())
                                 .name(record.getName());
    }
}
