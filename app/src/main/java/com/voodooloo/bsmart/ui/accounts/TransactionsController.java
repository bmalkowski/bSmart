package com.voodooloo.bsmart.ui.accounts;

import com.google.common.collect.ImmutableList;
import com.voodooloo.bsmart.investments.Transaction;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;

public class TransactionsController {
    final Formatter formatter;

    @FXML TableView<Transaction> transactionsView;
    @FXML TableColumn<Transaction, String> nameColumn;
    @FXML TableColumn<Transaction, String> symbolColumn;
    @FXML TableColumn<Transaction, String> dateColumn;
    @FXML TableColumn<Transaction, String> reasonColumn;
    @FXML TableColumn<Transaction, String> priceColumn;
    @FXML TableColumn<Transaction, String> quantityColumn;
    @FXML TableColumn<Transaction, String> valueColumn;

    @Inject
    public TransactionsController() {
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new SimpleValueFactory<>(transaction -> transaction.investment.name));
        symbolColumn.setCellValueFactory(new SimpleValueFactory<>(transaction -> transaction.investment.symbol));
        dateColumn.setCellValueFactory(new SimpleValueFactory<>(transaction -> transaction.tradeDate.toString()));
        reasonColumn.setCellValueFactory(new SimpleValueFactory<>(transaction -> transaction.reason));
        priceColumn.setCellValueFactory(new SimpleValueFactory<>(transaction -> formatter.formatAsCurrency(transaction.price)));
        quantityColumn.setCellValueFactory(new SimpleValueFactory<>(transaction -> formatter.formatAsQuantity(transaction.quantity)));
        valueColumn.setCellValueFactory(new SimpleValueFactory<>(transaction -> formatter.formatAsCurrency(transaction.value())));

        nameColumn.prefWidthProperty().bind(transactionsView.widthProperty().multiply(.28));
        symbolColumn.prefWidthProperty().bind(transactionsView.widthProperty().multiply(.12));
        dateColumn.prefWidthProperty().bind(transactionsView.widthProperty().multiply(.12));
        reasonColumn.prefWidthProperty().bind(transactionsView.widthProperty().multiply(.12));
        priceColumn.prefWidthProperty().bind(transactionsView.widthProperty().multiply(.12));
        quantityColumn.prefWidthProperty().bind(transactionsView.widthProperty().multiply(.12));
        valueColumn.prefWidthProperty().bind(transactionsView.widthProperty().multiply(.12));
    }

    public void setTransactions(ImmutableList<Transaction> transactions) {
        transactionsView.setItems(FXCollections.observableArrayList(transactions));
    }
}
