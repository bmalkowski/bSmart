package com.voodooloo.bsmart.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogAction;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javax.inject.Inject;
import java.util.HashMap;

public class AppController implements Controller {
    final App app;
    final PortfolioData portfolioData;
    final ControllerFactory controllerFactory;
    final HashMap<Tab, Controller> loadedControllers;

    BorderPane rootBorderPane;
    TabPane tabPane;

    @Inject
    public AppController(App app, PortfolioData portfolioData, ControllerFactory controllerFactory) {
        this.app = app;
        this.portfolioData = portfolioData;
        this.controllerFactory = controllerFactory;
        loadedControllers = new HashMap<>();
    }

    @Override
    public void load() {
        app.bus().register(this);

        Tab overviewTab = new Tab();
        overviewTab.setText("Overview");

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(overviewTab);

        tabPane.getSelectionModel().clearSelection();
        tabPane.getSelectionModel().selectedItemProperty().addListener(this::onTabSelected);
        tabPane.getSelectionModel().selectFirst();

        rootBorderPane = new BorderPane();
        rootBorderPane.setCenter(tabPane);
    }

    @Override
    public void unload() {
        app.bus().unregister(this);
    }

    @Override
    public Node node() {
        return rootBorderPane;
    }

    void onTabSelected(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        Controller oldController = loadedControllers.get(oldValue);
        if (oldController != null) {
            oldController.unload();
        }

        Controller newController = loadedControllers.get(newValue);
        if (newController == null) {
            Portfolio portfolio = (Portfolio)newValue.getUserData();
            newController = portfolio == null ?
                    controllerFactory.overviewController() :
                    controllerFactory.portfolioController(portfolio);
            loadedControllers.put(newValue, newController);
        }

        newController.load();
        newValue.setContent(newController.node());
    }

    void onAdd(ActionEvent event) {
        Action saveAction = new DialogAction("Save", ButtonBar.ButtonType.OK_DONE);
        ValidationSupport validationSupport = new ValidationSupport();
        Portfolio.Builder builder = new Portfolio.Builder();

        Text nameLabel = new Text("Name");
        TextField nameInput = new TextField();
        nameInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!validationSupport.isInvalid()) {
                    builder.name(newValue);
                }
            }
        });

        validationSupport.registerValidator(nameInput, Validator.createEmptyValidator("Name is required"));
        validationSupport.invalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                saveAction.setDisabled(newValue);
            }
        });

        GridPane gridPane = new GridPane();
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameInput, 1, 0);
        gridPane.getChildren().addAll(nameLabel, nameInput);

        final Dialog dialog = new Dialog(null, "New Portfolio", false);
        dialog.getStyleClass().add(Dialog.STYLE_CLASS_NATIVE);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(gridPane);
        dialog.getActions().setAll(saveAction, Dialog.ACTION_CANCEL);

        Action response = dialog.show();
        if (response == saveAction) {
            portfolioData.insert(builder.build());
        }
    }

    @Subscribe
    public void onEvent(PortfolioData.Event event) {
        ObservableList<Tab> tabs = tabPane.getTabs();
        if (tabs.size() > 1) {
            tabs.remove(1, tabs.size());
        }

        ImmutableList<Portfolio> portfolios = portfolioData.findAll();
        for (Portfolio porfolio : portfolios) {
            Tab tab = new Tab();
            tab.setText(porfolio.name);
            tab.setContent(new Text(porfolio.name + " content"));
            tab.setUserData(porfolio);
            tabs.add(tab);
        }
    }
}
