package com.voodooloo.bsmart.ui;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.ui.investments.InvestmentFactory;
import com.voodooloo.bsmart.ui.investments.InvestmentsController;
import com.voodooloo.bsmart.ui.portfolios.PortfoliosController;
import com.voodooloo.bsmart.ui.portfolios.PortfolioFactory;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javax.inject.Inject;
import java.util.HashMap;

public class AppController implements Controller {
    final App app;
    final PortfolioFactory portfolioFactory;
    final InvestmentFactory controllerFactory;
    final HashMap<Tab, Controller> loadedControllers;

    BorderPane rootBorderPane;
    HBox buttonBar;
    TabPane contentPane;

    @Inject
    public AppController(App app, PortfolioFactory portfolioFactory, InvestmentFactory controllerFactory) {
        this.app = app;
        this.portfolioFactory = portfolioFactory;
        this.controllerFactory = controllerFactory;
        loadedControllers = new HashMap<>();
    }

    @Override
    public void load() {
        app.bus().register(this);

        buttonBar = buildButtonBar();
        contentPane = buildContentPane();

        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(buttonBar);
        rootBorderPane.setCenter(contentPane);
    }

    TabPane buildContentPane() {
        PortfoliosController portfoliosController = portfolioFactory.portfoliosController();
        Tab overviewTab = new Tab();
        overviewTab.setText("Portfolios");
        overviewTab.setUserData(portfoliosController);
        loadedControllers.put(overviewTab, portfoliosController);

        InvestmentsController investmentsController = controllerFactory.investmentsController();
        Tab investmentsTab = new Tab();
        investmentsTab.setText("Investments");
        investmentsTab.setUserData(investmentsController);
        loadedControllers.put(investmentsTab, investmentsController);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(overviewTab, investmentsTab);

        tabPane.getSelectionModel().clearSelection();
        tabPane.getSelectionModel().selectedItemProperty().addListener(this::onTabSelected);
        tabPane.getSelectionModel().selectFirst();
        return tabPane;
    }

    HBox buildButtonBar() {
        Button portfolioButton = new Button("New Portfolio");
        AwesomeDude.setIcon(portfolioButton, AwesomeIcon.CALCULATOR, ContentDisplay.TOP);
        portfolioButton.setOnAction(this::onAddPortfolio);
        portfolioButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(portfolioButton, Priority.ALWAYS);

        Button investmentButton = new Button("New Investment");
        AwesomeDude.setIcon(investmentButton, AwesomeIcon.DOLLAR, ContentDisplay.TOP);
        investmentButton.setOnAction(this::onAddPortfolio);
        investmentButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(investmentButton, Priority.ALWAYS);

        Button accountButton = new Button("New Account");
        AwesomeDude.setIcon(accountButton, AwesomeIcon.FOLDER, ContentDisplay.TOP);
        accountButton.setOnAction(this::onAddPortfolio);
        accountButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(accountButton, Priority.ALWAYS);

        Button firmButton = new Button("New Firm");
        AwesomeDude.setIcon(firmButton, AwesomeIcon.BANK, ContentDisplay.TOP);
        firmButton.setOnAction(this::onAddPortfolio);
        firmButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(firmButton, Priority.ALWAYS);

        return new HBox(portfolioButton, investmentButton, accountButton, firmButton);
    }

    @Override
    public void unload() {
        app.bus().unregister(this);
    }

    @Override
    public Node node() {
        return rootBorderPane;
    }

    void onAddPortfolio(ActionEvent event) {
        portfolioFactory.showNewDialog();
    }

    void onTabSelected(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        Controller oldController = loadedControllers.get(oldValue);
        if (oldController != null) {
            oldController.unload();
        }

        Controller newController = loadedControllers.get(newValue);
        newController.load();
        newValue.setContent(newController.node());
    }
}
