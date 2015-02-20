package com.voodooloo.bsmart.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioData;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import java.util.HashMap;

public class AppController implements Controller {
    final App app;
    final PortfolioData portfolioData;
    final ControllerFactory controllerFactory;
    final HashMap<Tab, Controller> loadedControllers;

    BorderPane rootBorderPane;
    TabPane tabPane;
    Tab overviewTab;

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

        OverviewController overviewController = controllerFactory.overviewController();
        InvestmentsController investmentsController = controllerFactory.investmentsController();

        overviewTab = new Tab();
        overviewTab.setText("Overview");
        overviewTab.setUserData(overviewController);
        loadedControllers.put(overviewTab, overviewController);

        Tab investmentsTab = new Tab();
        investmentsTab.setText("Investments");
        investmentsTab.setUserData(investmentsController);
        loadedControllers.put(investmentsTab, investmentsController);

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(overviewTab, investmentsTab);

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
        newController.load();
        newValue.setContent(newController.node());
    }

    @Subscribe
    public void onEvent(PortfolioData.Event event) {
        ObservableList<Tab> tabs = tabPane.getTabs();
        if (tabs.size() > 2) {
            tabs.remove(2, tabs.size());
        }

        ImmutableList<Portfolio> portfolios = portfolioData.findAll();
        for (Portfolio porfolio : portfolios) {
            Tab tab = new Tab();
            tab.setText(porfolio.name);
            tab.setUserData(controllerFactory.portfolioController(porfolio));
            tabs.add(tab);
        }
    }
}
