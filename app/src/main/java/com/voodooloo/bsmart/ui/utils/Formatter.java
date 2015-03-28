package com.voodooloo.bsmart.ui.utils;

import org.joda.money.BigMoney;
import org.joda.money.format.MoneyAmountStyle;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class Formatter {
    final NumberFormat quantityFormatter;
    final MoneyFormatter currencyFormatter;

    public Formatter() {
        quantityFormatter = NumberFormat.getNumberInstance();
        currencyFormatter = new MoneyFormatterBuilder().appendCurrencySymbolLocalized()
                                                       .appendAmount(MoneyAmountStyle.LOCALIZED_GROUPING)
                                                       .toFormatter();
    }

    public String formatAsQuantity(BigDecimal number) {
        return quantityFormatter.format(number);
    }

    public String formatAsCurrency(BigMoney money) {
        return currencyFormatter.print(money.withCurrencyScale(RoundingMode.HALF_EVEN));
    }
}
