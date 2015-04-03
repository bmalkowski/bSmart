package com.voodooloo.bsmart.investments;

import com.voodooloo.bsmart.generated.tables.records.CategoryRecord;

public class CategoryDAO {
    public Category.Builder builderFrom(CategoryRecord record) {
        return new Category.Builder().id(record.getId())
                                     .name(record.getName());
    }

}
