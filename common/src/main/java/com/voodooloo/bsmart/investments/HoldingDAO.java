package com.voodooloo.bsmart.investments;

import com.voodooloo.bsmart.generated.tables.records.HoldingRecord;

public class HoldingDAO {
    public Holding.Builder builderFrom(HoldingRecord record) {
        return new Holding.Builder().id(record.getId())
                                    .quantity(record.getQuantity());
    }
}
