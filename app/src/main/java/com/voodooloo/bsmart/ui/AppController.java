package com.voodooloo.bsmart.ui;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.ui.investments.InvestmentFactory;
import com.voodooloo.bsmart.ui.portfolios.PortfolioFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

public class AppController {
    final App app;
    final PortfolioFactory portfolioFactory;
    final InvestmentFactory controllerFactory;

    @FXML BorderPane root;

    @Inject
    public AppController(App app, PortfolioFactory portfolioFactory, InvestmentFactory controllerFactory) {
        this.app = app;
        this.portfolioFactory = portfolioFactory;
        this.controllerFactory = controllerFactory;
    }

    @FXML
    public void initialize() {
        app.bus().register(this);
    }

    public void onAddPortfolio(ActionEvent event) {
        portfolioFactory.showNewDialog();
    }
}
