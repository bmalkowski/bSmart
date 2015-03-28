package com.voodooloo.bsmart.ui.accounts;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.investments.Holding;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.joda.money.BigMoney;

import javax.inject.Inject;
import java.math.BigDecimal;

public class SummaryController {
    final App app;
    final AccountDAO accountDAO;
    final Formatter formatter;

    Account account;

    @FXML VBox root;

    @FXML HBox header;
    @FXML Text nameText;
    @FXML Text valueText;

    @FXML PieChart allocations;

    @FXML TableView<Holding> summary;
    @FXML TableColumn<Holding, String> nameColumn;
    @FXML TableColumn<Holding, String> symbolColumn;
    @FXML TableColumn<Holding, String> priceColumn;
    @FXML TableColumn<Holding, String> quantityColumn;
    @FXML TableColumn<Holding, String> valueColumn;

    @Inject
    public SummaryController(App app, AccountDAO accountDAO) {
        this.app = app;
        this.accountDAO = accountDAO;
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> holding.investment.name));
        symbolColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> holding.investment.symbol));
        priceColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> formatter.formatAsCurrency(holding.investment.price)));
        quantityColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> formatter.formatAsQuantity(holding.quantity)));
        valueColumn.setCellValueFactory(new SimpleValueFactory<>(holding -> formatter.formatAsCurrency(holding.value())));

        nameColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.4));
        symbolColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.15));
        priceColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.15));
        quantityColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.15));
        valueColumn.prefWidthProperty().bind(summary.widthProperty().multiply(.15));

        allocations.setLegendSide(Side.LEFT);
    }

    public void setAccount(Account account) {
        this.account = account;
        BigMoney accountValue = account.value();

        nameText.setText(account.name);
        valueText.setText(formatter.formatAsCurrency(accountValue));

        ObservableList<Holding> holdings = FXCollections.observableArrayList(account.holdings);
        summary.setItems(holdings);

        ObservableList<PieChart.Data> allocationData = allocations.getData();
        allocationData.clear();
        for (Holding holding : holdings) {
            BigDecimal percentage = holding.value().getAmount().divide(accountValue.getAmount(), BigDecimal.ROUND_HALF_EVEN);
            int percent = percentage.multiply(BigDecimal.valueOf(100)).intValue();
            allocationData.add(new PieChart.Data(holding.investment.symbol, percent));
        }
    }
}
