package com.voodooloo.bsmart.net;

import com.voodooloo.bsmart.investments.Investment;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pmw.tinylog.Logger;

import java.math.BigDecimal;
import java.util.Optional;

public class MutualFundProvider {
    static final String NAME_URL = "http://quotes.morningstar.com/fund/f?ops=p&region=usa&culture=en-US&t=";
    static final String HEADER_URL = "http://quotes.morningstar.com/fund/c-header?region=usa&culture=en-US&ops=p&cur=USD&t=";
    static final String PERF_URL = "http://quotes.morningstar.com/fund/c-performance?region=usa&culture=en-US&ops=p&cur=USD&t=";

    public Optional<Investment> get(String symbol) {
        Investment investment = null;
        try {
            Document nameDocument = Jsoup.connect(NAME_URL + symbol).get();
            Document headerDocument = Jsoup.connect(HEADER_URL + symbol).get();

            String name = nameDocument.select(".reports_nav .r_title h1").text().trim();
            String nav = headerDocument.select("span[vkey=NAV]").text().trim();

            Investment.Builder builder = new Investment.Builder();
            investment = builder.symbol(symbol)
                                .name(name)
                                .price(BigMoney.of(CurrencyUnit.USD, new BigDecimal(nav)))
                                .build();
        } catch (Exception e) {
            Logger.error(e);
        }

        return Optional.ofNullable(investment);
    }

//    public Investment get(String symbol) {
//        try {
//            Document nameDocument = Jsoup.connect(NAME_URL + symbol).get();
//            Document headerDocument = Jsoup.connect(HEADER_URL + symbol.ticker).get();
//            Document performanceDocument = Jsoup.connect(PERF_URL + symbol.ticker).get();
//
//            String name = nameDocument.select(".reports_nav .r_title h1").text().trim();
//            String nav = headerDocument.select("span[vkey=NAV]").text().trim();
//            String category = headerDocument.select("span[vkey=MorningstarCategory]").text().trim();
//            String expenseRatio = headerDocument.select("span[vkey=ExpenseRatio]").text().trim();
//
//            Element fundRow = performanceDocument.select("table tr:eq(3)").first();
//            String returnYTD = fundRow.child(1).text();
//            String return1Year = fundRow.child(3).text();
//            String return3Year = fundRow.child(5).text();
//            String return5Year = fundRow.child(7).text();
//
//            FundQuote.Builder builder = new FundQuote.Builder()
//                    .symbol(symbol)
//                    .name(name)
//                    .category(category)
//                    .expenseRatio(new BigDecimal(expenseRatio.replace("%", "")).divide(BigDecimal.valueOf(100)))
//                    .close(Money.of(CurrencyUnit.USD, new BigDecimal(nav)))
//                    .returnYTD(new BigDecimal(returnYTD).divide(BigDecimal.valueOf(100)))
//                    .return1Year(new BigDecimal(return1Year).divide(BigDecimal.valueOf(100)))
//                    .return3Year(new BigDecimal(return3Year).divide(BigDecimal.valueOf(100)))
//                    .return5Year(new BigDecimal(return5Year).divide(BigDecimal.valueOf(100)));
//            return Optional.of(builder.build());
//        } catch (Exception e) {
//            return Optional.empty();
//        }
//    }
}
