package com.voodooloo.bsmart.ui;

import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

public class AppController {
    final FXMLProvider fxmlProvider;

    @FXML BorderPane root;

    @Inject
    public AppController(FXMLProvider fxmlProvider) {
        this.fxmlProvider = fxmlProvider;
    }

    @FXML
    public void initialize() {
        onAccounts(null);
    }

    public void onPortfolios(ActionEvent event) {
        ViewController<Node, ?> viewController = fxmlProvider.load("layouts/portfolios.fxml");
        root.setCenter(viewController.view);
    }

    public void onAccounts(ActionEvent event) {
        ViewController<Node, ?> viewController = fxmlProvider.load("layouts/accounts.fxml");
        root.setCenter(viewController.view);
    }
}
