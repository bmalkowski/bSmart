package com.voodooloo.bsmart.investments;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.generated.tables.MutualFund;
import com.voodooloo.bsmart.generated.tables.records.AccountRecord;
import com.voodooloo.bsmart.generated.tables.records.CategoryRecord;
import com.voodooloo.bsmart.generated.tables.records.InvestmentCategoryRecord;
import com.voodooloo.bsmart.generated.tables.records.InvestmentRecord;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jooq.DSLContext;

import java.util.ArrayList;

import static com.voodooloo.bsmart.generated.Tables.*;

public class InvestmentDAO {
    final DSLContext context;
    final EventBus bus;

    public InvestmentDAO(DSLContext context, EventBus bus) {
        this.context = context;
        this.bus = bus;
    }

    public void insert(MutualFund fund) {
        context.insertInto(INVESTMENT)
               .set(INVESTMENT.SYMBOL, fund.symbol)
               .set(INVESTMENT.NAME, fund.name)
               .set(INVESTMENT.INVESTMENT_TYPE_ID, fund.name)
               .execute();
        bus.post();
    }

    public Investment find(Integer id) {
        CategoryDAO categoryDAO = new CategoryDAO();

        ArrayList<Investment> investments = new ArrayList<>();
        context.select()
               .from(INVESTMENT.join(INVESTMENT_CATEGORY.join(CATEGORY)
                                                        .onKey())
                               .onKey())
               .where(INVESTMENT.ID.equal(id))
               .fetchGroups(INVESTMENT)
               .forEach((investmentRecord, records) -> {
                   ImmutableList.Builder<PartialCategory> categories = ImmutableList.builder();
                   records.forEach(record -> {
                       CategoryRecord categoryRecord = record.into(CATEGORY);
                       Category.Builder categoryBuilder = categoryDAO.builderFrom(categoryRecord);

                       InvestmentCategoryRecord investmentCategoryRecord = record.into(INVESTMENT_CATEGORY);
                       PartialCategory.Builder partialCategoryBuilder = new PartialCategory.Builder()
                               .percentage(investmentCategoryRecord.getPercentage())
                               .category(categoryBuilder.build());

                       categories.add(partialCategoryBuilder.build());
                   });

                   Investment.Builder builder = builderFrom(investmentRecord);
                   builder.partialCategories(categories.build());
                   investments.add(builder.build());
               });
        return investments.isEmpty() ? null : investments.get(0);
    }

    public ImmutableList<Investment> findAll() {
        CategoryDAO categoryDAO = new CategoryDAO();

        ImmutableList.Builder<Investment> investments = ImmutableList.builder();
        context.select()
               .from(INVESTMENT.join(INVESTMENT_CATEGORY.join(CATEGORY)
                                                        .onKey())
                               .onKey())
               .fetchGroups(INVESTMENT)
               .forEach((investmentRecord, records) -> {
                   ImmutableList.Builder<PartialCategory> categories = ImmutableList.builder();
                   records.forEach(record -> {
                       CategoryRecord categoryRecord = record.into(CATEGORY);
                       Category.Builder categoryBuilder = categoryDAO.builderFrom(categoryRecord);

                       InvestmentCategoryRecord investmentCategoryRecord = record.into(INVESTMENT_CATEGORY);
                       PartialCategory.Builder partialCategoryBuilder = new PartialCategory.Builder()
                               .percentage(investmentCategoryRecord.getPercentage())
                               .category(categoryBuilder.build());

                       categories.add(partialCategoryBuilder.build());
                   });

                   Investment.Builder builder = builderFrom(investmentRecord);
                   builder.partialCategories(categories.build());
                   investments.add(builder.build());
               });
        return investments.build();
    }

    public ImmutableList<InvestmentType> findTypes() {
        ImmutableList.Builder<InvestmentType> types = ImmutableList.builder();
        context.selectFrom(INVESTMENT_TYPE).forEach(record -> types.add(new InvestmentType.Builder().id(record.getId())
                                                                                                    .name(record.getName())
                                                                                                    .build()));
        return types.build();
    }

    public Investment.Builder builderFrom(InvestmentRecord record) {
        return new Investment.Builder().id(record.getId())
                                       .name(record.getName())
                                       .symbol(record.getSymbol())
                                       .price(BigMoney.of(CurrencyUnit.USD, record.getPrice()));
    }
}
