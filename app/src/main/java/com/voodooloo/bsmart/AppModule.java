package com.voodooloo.bsmart;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.ui.AppController;
import com.voodooloo.bsmart.ui.accounts.AccountsController;
import com.voodooloo.bsmart.ui.accounts.ListController;
import com.voodooloo.bsmart.ui.accounts.SummaryController;
import com.voodooloo.bsmart.ui.accounts.TransactionsController;
import com.voodooloo.bsmart.ui.dialogs.AddAccountDialog;
import com.voodooloo.bsmart.ui.dialogs.AddInvestmentDialog;
import com.voodooloo.bsmart.ui.dialogs.AddTransactionDialog;
import com.voodooloo.bsmart.ui.dialogs.ProgressDialog;
import com.voodooloo.bsmart.ui.investments.InvestmentFactory;
import com.voodooloo.bsmart.ui.investments.InvestmentsController;
import com.voodooloo.bsmart.ui.portfolios.PortfolioFactory;
import com.voodooloo.bsmart.ui.portfolios.PortfoliosController;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ForegroundExecutor;
import dagger.Module;
import dagger.Provides;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Module(injects = {
        App.class,

        AppController.class,

        PortfoliosController.class,
        com.voodooloo.bsmart.ui.portfolios.SummaryController.class,

        AccountsController.class,
        SummaryController.class,
        TransactionsController.class,
        ListController.class,

        InvestmentsController.class,

        ProgressDialog.class,
        AddTransactionDialog.class,
        AddAccountDialog.class,
        AddInvestmentDialog.class,

        PortfolioFactory.class,
        InvestmentFactory.class,
})
public class AppModule {
    final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    App provideApp() {
        return app;
    }

    @Singleton
    @Provides
    DataSource provideDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:ismart;DB_CLOSE_DELAY=-1");
        return dataSource;
    }

    @Provides
    DSLContext provideDSLContext(DataSource dataSource) {
        Configuration configuration = new DefaultConfiguration()
                .set(dataSource)
                .set(SQLDialect.H2);
        return DSL.using(configuration);
    }

    @Singleton
    @Provides
    EventBus provideEventBus() {
        return new EventBus();
    }

    @Singleton
    @Provides
    FXMLProvider provideFXMLProvider(App app) {
        return new FXMLProvider(app.objectGraph);
    }


    @Singleton
    @Provides
    ForegroundExecutor provideForegroundExecutor(FXMLProvider provider) {
        return new ForegroundExecutor(provider, 5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    @Singleton
    @Provides
    CloseableHttpClient provideCloseableHttpClient() {
        return HttpClients.createDefault();
    }

    @Singleton
    @Provides
    CsvMapper provideCsvMapper() {
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        return mapper;
    }
}
