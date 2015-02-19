package com.voodooloo.bsmart;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.ui.AppController;
import com.voodooloo.bsmart.ui.ControllerFactory;
import com.voodooloo.bsmart.ui.OverviewController;
import dagger.Module;
import dagger.Provides;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module(injects = {
        App.class,

        AppController.class,
        OverviewController.class,

        ControllerFactory.class,
}, addsTo = DataModule.class)
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
        Configuration configuration = new DefaultConfiguration().set(dataSource).set(SQLDialect.H2);
        return DSL.using(configuration);
    }

    @Singleton
    @Provides
    EventBus provideEventBus() {
        return new EventBus();
    }
}
