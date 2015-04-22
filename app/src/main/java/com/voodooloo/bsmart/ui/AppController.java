package com.voodooloo.bsmart.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.ui.dialogs.AddAccountDialog;
import com.voodooloo.bsmart.ui.dialogs.AddTransactionDialog;
import com.voodooloo.bsmart.utils.BusController;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

public class AppController extends BusController {
    final FXMLProvider fxmlProvider;

    @FXML BorderPane root;
    @FXML TitledPane portfoliosPane;
    @FXML TitledPane accountsPane;

    ViewController<Node, ?> centerController;

    @Inject
    public AppController(FXMLProvider fxmlProvider, EventBus bus) {
        super(bus);
        this.fxmlProvider = fxmlProvider;
    }

    @FXML
    public void initialize() {
        ViewController<Node, ?> portfoliosController = fxmlProvider.load("layouts/portfolios.fxml");
        portfoliosPane.setContent(portfoliosController.transition(null));

        ViewController<Node, ?> accountsController = fxmlProvider.load("layouts/accounts/list.fxml");
        accountsPane.setContent(accountsController.transition(null));
    }

    public void onAddTransaction() {
        ViewController<Node, AddTransactionDialog> viewController = fxmlProvider.load("dialogs/addTransaction.fxml");
        viewController.controller.show(viewController.transition(null));
    }

    public void onAddAccount() {
        ViewController<Node, AddAccountDialog> viewController = fxmlProvider.load("dialogs/addAccount.fxml");
        viewController.controller.show(viewController.transition(null));
    }

    @Subscribe
    public void onEvent(Center center) {
        root.setCenter(center.viewController.transition(centerController));
        centerController = center.viewController;
    }

    public static class Center
    {
        public final ViewController<Node, ?> viewController;

        public Center(ViewController<Node, ?> viewController) {
            this.viewController = viewController;
        }
    }
}
