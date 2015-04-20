package com.voodooloo.bsmart.ui.accounts;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.utils.BusController;
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

public class AccountsController extends BusController {
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

    ViewController<Node, ?> centerController;

    @Inject
    public AccountsController(FXMLProvider fxmlProvider, DSLContext context, EventBus bus) {
        super(bus);

        this.fxmlProvider = fxmlProvider;
        accountDAO = new AccountDAO(context, bus);
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
    }

    public void updateAccount(Account account) {
        this.account = account;

        nameText.setText(account.name);
        valueText.setText(formatter.formatAsCurrency(account.value()));
        firmText.setText(account.firm.name);

        summaryButton.fire();
    }

    @Subscribe
    public void onEvent(Account account) {
        updateAccount(account);
    }

    public void onSummary(ActionEvent event) {
        ViewController<Node, SummaryController> viewController = fxmlProvider.load("layouts/accounts/summary.fxml");
        viewController.controller.setAccount(account);
        root.setCenter(viewController.transition(centerController));
        centerController = viewController;
    }

    public void onTransactions(ActionEvent event) {
        ViewController<Node, TransactionsController> viewController = fxmlProvider.load("layouts/accounts/transactions.fxml");
        viewController.controller.setTransactions(accountDAO.transactionsFor(account));
        root.setCenter(viewController.transition(centerController));
        centerController = viewController;
    }
}
