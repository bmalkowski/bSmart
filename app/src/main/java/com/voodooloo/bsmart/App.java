package com.voodooloo.bsmart;

import com.google.common.eventbus.EventBus;
import com.google.common.io.Resources;
import com.voodooloo.bsmart.ui.AppController;
import com.voodooloo.bsmart.utils.DaggerFXMLLoader;
import dagger.ObjectGraph;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;

import javax.inject.Inject;

public class App extends Application {
    final ObjectGraph objectGraph;

    @Inject AppController appController;
    @Inject DSLContext context;
    @Inject EventBus bus;

    public EventBus bus() {
        return bus;
    }

    public DSLContext context() {
        return context;
    }

    public App() {
        objectGraph = ObjectGraph.create(new DataModule(), new AppModule(this));
        objectGraph.inject(this);

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
//        URL url = Resources.getResource("app.fxml");
//        Node node = FXMLLoader.load(url);
//        appController.load();
        DaggerFXMLLoader loader = new DaggerFXMLLoader(objectGraph);
        Node node = loader.load(Resources.getResource("layouts/app.fxml"));
        Scene scene = new Scene((Parent)node, 800, 600);

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
