package com.voodooloo.bsmart;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.ui.AppController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public class App extends Application {
//    final Injector injector;

//    @Inject
//    GuiceFXMLLoader loader;

//    MasterDetailsController rootController;

    final AppController appController;

    public App() {
//        injector = Guice.createInjector(new Module());
//        injector.injectMembers(this);

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:ismart;DB_CLOSE_DELAY=-1");

        Configuration configuration = new DefaultConfiguration().set(dataSource).set(SQLDialect.H2);
        DSLContext context = DSL.using(configuration);
        EventBus bus = new EventBus();

        appController = new AppController(context, bus);

//        Configuration configuration = new DefaultConfiguration().set(dataSource).set(SQLDialect.H2);
//        DSLContext context = DSL.using(configuration);

//        PortfolioDAO dao = new PortfolioDAO(context);
//
//        try {
//            new Data(dataSource).generateTables();
//
//            dao.insert(new Portfolio(null, "test"));
//
//            rootController = new MasterDetailsController();
//            rootController.setMasterController(new PortfoliosController(dao));
//            rootController.setDetailsController(new OverviewController(dao));
//
//        } catch (Exception e) {
//            Logger.error(e);
//        }
//
////        Database database = new Database(dataSource);
////        new Tables().create(database);
//
//        new Thread(() -> {
//
////      Fund fund = new Fund.Builder().symbol(new Symbol("vbtlx")).build();
////      YahooFinanceSource financeSource = injector.getInstance(YahooFinanceSource.class);
////      Quote quote = financeSource.quote(fund);
////      Logger.info(quote);
////      YahooSearchEngine engine = injector.getInstance(YahooSearchEngine.class);
////      ImmutableList<YahooResult> appl = engine.search("appl");
////      Logger.info(appl);
////
////      ImmutableList<YahooResult> vbtlx = engine.search("vbtlx");
////      Logger.info(vbtlx);
////
////      engine.quote(new Stock.Builder().symbol(new Symbol("appl")).build());
//
//        }).start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene((Parent)appController.node(), 800, 600);

        primaryStage.setTitle("bSmart");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:ismart;DB_CLOSE_DELAY=-1");

        Builder builder = new Builder(dataSource);
        builder.buildDatabase();
        
        launch(args);
    }
}
