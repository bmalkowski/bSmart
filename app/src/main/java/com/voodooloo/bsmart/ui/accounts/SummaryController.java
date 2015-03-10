package com.voodooloo.bsmart.ui.accounts;

import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.investments.Holding;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import javax.inject.Inject;

public class SummaryController {
    final App app;
    final AccountDAO accountDAO;
    final Formatter formatter;
    Account account;

    @FXML BorderPane root;
    @FXML HBox header;
    @FXML Text nameText;
    @FXML Text valueText;

    @FXML TableView<Holding> summary;
    @FXML TableColumn<Holding, String> nameColumn;
    @FXML TableColumn<Holding, String> quantityColumn;
    @FXML TableColumn<Holding, String> valueColumn;

    @Inject
    public SummaryController(App app, AccountDAO accountDAO) {
        this.app = app;
        this.accountDAO = accountDAO;
        formatter = new Formatter();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> holding.investment.name));
        quantityColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> formatter.formatAsQuantity(holding.quantity)));
        quantityColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> formatter.formatAsCurrency(holding.value())));

        nameText.setText(account.name);
        valueText.setText(formatter.formatAsCurrency(account.value()));

        ObservableList<Holding> holdings = FXCollections.observableArrayList(account.holdings);
        summary.setItems(holdings);
    }

    @Subscribe
    public void onEvent(AccountDAO.Event event) {

    }
}
