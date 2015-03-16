package com.voodooloo.bsmart.ui.accounts;

import com.voodooloo.bsmart.investments.*;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import com.voodooloo.bsmart.utils.FXMLProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javax.inject.Inject;


public class AccountsController {
  final FXMLProvider fxmlProvider;
  final AccountDAO accountDAO;
  final Formatter formatter;

  @FXML TableView<Account> accountTable;
  @FXML TableColumn<Account, String> nameColumn;
  @FXML TableColumn<Account, String> totalColumn;

  @Inject
  public AccountsController(AccountDAO accountDAO, FXMLProvider fxmlProvider) {
    this.accountDAO = accountDAO;
    this.fxmlProvider = fxmlProvider;
    formatter = new Formatter();
  }

  @FXML
  public void initialize() {
    nameColumn.setCellValueFactory(new SimpleValueFactory<>(account -> account.name));
    totalColumn.setCellValueFactory(new SimpleValueFactory<>(account -> formatter.formatAsCurrency(account.value())));
    updateAccounts();
  }

  void updateAccounts() {
    ObservableList<Account> accounts = FXCollections.observableArrayList(accountDAO.findAll());
    accountTable.setItems(accounts);
  }
}
