package com.voodooloo.bsmart;

import com.voodooloo.bsmart.ui.AppController;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ViewController;
import dagger.ObjectGraph;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.h2.jdbcx.JdbcDataSource;

import javax.inject.Inject;

public class App extends Application {
    final ObjectGraph objectGraph;

    @Inject FXMLProvider fxmlProvider;

    public App() {
        objectGraph = ObjectGraph.create(new AppModule(this));
        objectGraph.inject(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewController<Parent, AppController> viewController = fxmlProvider.load("layouts/app.fxml");
        Scene scene = new Scene(viewController.view, 1280, 720);
        scene.getStylesheets().add("css/app.css");

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
