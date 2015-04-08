package com.voodooloo.bsmart.ui.accounts;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.ui.AppController;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jooq.DSLContext;

import javax.inject.Inject;

public class ListController {
    final FXMLProvider fxmlProvider;
    final EventBus eventBus;
    final AccountDAO accountDAO;
    final Formatter formatter;

    @FXML TableView<Account> accountTable;
    @FXML TableColumn<Account, String> nameColumn;
    @FXML TableColumn<Account, String> totalColumn;

    @Inject
    public ListController(FXMLProvider fxmlProvider, DSLContext context, EventBus eventBus) {
        this.fxmlProvider = fxmlProvider;
        this.eventBus = eventBus;
        accountDAO = new AccountDAO(context, eventBus);
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
        accountTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ViewController<Node, AccountsController> viewController = fxmlProvider.load("layouts/accounts/accounts.fxml");
                viewController.controller.setAccount(newValue);
                eventBus.post(new AppController.Center(viewController));
            }
        });

        nameColumn.setCellValueFactory(new SimpleValueFactory<>(account -> account.name));
        totalColumn.setCellValueFactory(new SimpleValueFactory<>(account -> formatter.formatAsCurrency(account.value())));
        updateAccounts();
    }

    void updateAccounts() {
        ObservableList<Account> accounts = FXCollections.observableArrayList(accountDAO.findAll());
        accountTable.setItems(accounts);
    }
}
