package com.voodooloo.bsmart.net;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.voodooloo.bsmart.investments.MutualFund;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.pmw.tinylog.Logger;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

public class MutualFundProvider {
    static final String YAHOO_URL = "http://finance.yahoo.com/d/quotes.csv?f=snl1&s=";

    final CloseableHttpClient httpClient;
    final CsvMapper csvMapper;

    @Inject
    public MutualFundProvider(CloseableHttpClient httpClient, CsvMapper csvMapper) {
        this.httpClient = httpClient;
        this.csvMapper = csvMapper;
    }

    public Optional<MutualFund> get(String symbol) {
        String url = YAHOO_URL + symbol;
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
            String content = EntityUtils.toString(response.getEntity());
            return Optional.ofNullable(parseGetResponse(content));
        } catch (Exception e) {
            Logger.info(e, "Error getting Yahoo quote results");
            return Optional.empty();
        }
    }

    MutualFund parseGetResponse(String content) throws Exception {
        MappingIterator<String[]> it = csvMapper.reader(String[].class).readValues(content);
        if (!it.hasNext()) {
            return null;
        }

        String[] row = it.next();
        MutualFund.Builder builder = new MutualFund.Builder();
        builder.symbol(row[0])
               .name(row[1])
               .price(BigMoney.of(CurrencyUnit.USD, new BigDecimal(row[2])));
        return builder.build();
    }
}
