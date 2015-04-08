package com.voodooloo.bsmart.ui.accounts;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import javax.inject.Inject;

public class AccountsController {
    final FXMLProvider fxmlProvider;
    final EventBus eventBus;
    final Formatter formatter;

    Account account;

    @FXML BorderPane root;

    @FXML Text nameText;
    @FXML Text valueText;
    @FXML Text firmText;

    @FXML ToggleButton summaryButton;
    @FXML ToggleButton transactionsButton;


    @Inject
    public AccountsController(FXMLProvider fxmlProvider, EventBus eventBus) {
        this.fxmlProvider = fxmlProvider;
        this.eventBus = eventBus;
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
        eventBus.register(this);
    }

    public void setAccount(Account account) {
        this.account = account;

        nameText.setText(account.name);
        valueText.setText(formatter.formatAsCurrency(account.value()));
        firmText.setText(account.firm.name);

        summaryButton.fire();
    }

    public void onSummary(ActionEvent event)
    {
        ViewController<Node, SummaryController> summaryController = fxmlProvider.load("layouts/accounts/summary.fxml");
        summaryController.controller.setAccount(account);
        root.setCenter(summaryController.view);
    }

    public void onTransactions(ActionEvent event)
    {
//        ViewController<Node, SummaryController> summaryController = fxmlProvider.load("layouts/accounts/summary.fxml");
//        summaryController.controller.setAccount(account);
//        root.setCenter(summaryController.view);
        root.setCenter(null);
    }

    @Subscribe
    public void onEvent(Center center) {
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
