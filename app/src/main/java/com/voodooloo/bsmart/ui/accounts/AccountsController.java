package com.voodooloo.bsmart.ui.accounts;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.jooq.DSLContext;

import javax.inject.Inject;

public class AccountsController {
    final FXMLProvider fxmlProvider;
    final AccountDAO accountDAO;
    final Formatter formatter;

    Account account;

    @FXML BorderPane root;

    @FXML Text nameText;
    @FXML Text valueText;
    @FXML Text firmText;

    @FXML ToggleButton summaryButton;
    @FXML ToggleButton transactionsButton;

    @Inject
    public AccountsController(FXMLProvider fxmlProvider, DSLContext context, EventBus bus) {
        this.fxmlProvider = fxmlProvider;
        accountDAO = new AccountDAO(context, bus);
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
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
        ViewController<Node, SummaryController> viewController = fxmlProvider.load("layouts/accounts/summary.fxml");
        viewController.controller.setAccount(account);
        root.setCenter(viewController.view);
    }

    public void onTransactions(ActionEvent event)
    {
        ViewController<Node, TransactionsController> viewController = fxmlProvider.load("layouts/accounts/transactions.fxml");
        viewController.controller.setTransactions(accountDAO.transactionsFor(account));
        root.setCenter(viewController.view);
    }
}
