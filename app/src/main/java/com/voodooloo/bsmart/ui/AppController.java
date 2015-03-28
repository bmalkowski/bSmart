package com.voodooloo.bsmart.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

public class AppController {
    final FXMLProvider fxmlProvider;
    final EventBus eventBus;

    @FXML BorderPane root;

    @Inject
    public AppController(FXMLProvider fxmlProvider, EventBus eventBus) {
        this.fxmlProvider = fxmlProvider;
        this.eventBus = eventBus;
    }

    @FXML
    public void initialize() {
        eventBus.register(this);
    }

    public void onPortfolios(ActionEvent event) {
        ViewController<Node, ?> viewController = fxmlProvider.load("layouts/portfolios.fxml");
        root.setCenter(viewController.view);
    }

    @Subscribe public void onEvent(Center center) {
        root.setCenter(center.viewController.view);
    }

    public static class Center
    {
        public final ViewController<Node, ?> viewController;

        public Center(ViewController<Node, ?> viewController) {
            this.viewController = viewController;
        }
    }
}
