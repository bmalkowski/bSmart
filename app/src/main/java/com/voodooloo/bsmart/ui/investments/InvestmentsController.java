package com.voodooloo.bsmart.ui.investments;

import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.investments.Holding;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.joda.money.Money;
import org.joda.money.format.MoneyAmountStyle;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;

import javax.inject.Inject;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class InvestmentsController {
    final App app;
    final AccountDAO accountDAO;
    final NumberFormat quantityFormatter;
    final MoneyFormatter currencyFormatter;

    @FXML BorderPane root;
    @FXML VBox accountsBox;

    @Inject
    public InvestmentsController(App app, AccountDAO accountDAO) {
        this.app = app;
        this.accountDAO = accountDAO;

        quantityFormatter = NumberFormat.getNumberInstance();
        currencyFormatter = new MoneyFormatterBuilder().appendCurrencySymbolLocalized()
                                                       .appendAmount(MoneyAmountStyle.LOCALIZED_GROUPING)
                                                       .toFormatter();
    }

    @FXML
    public void initialize() {
        app.bus().register(this);

        rebuildAccounts();
    }

    void rebuildAccounts() {
        accountsBox.getChildren().clear();

        for (Account account : accountDAO.findAll()) {
            GridPane gridPane = new GridPane();
            accountsBox.getChildren().add(gridPane);

            Money holdingValue = account.value().toMoney(RoundingMode.HALF_EVEN);

            int row = 0;
            gridPane.add(new Text(account.name), 0, row);
            gridPane.add(new Text(currencyFormatter.print(holdingValue)), 1, row++);

            gridPane.add(new Text("Funds"), 0, row++);
            gridPane.add(new Text("Name"), 0, row);
            gridPane.add(new Text("Quantity"), 1, row);
            gridPane.add(new Text("Value"), 2, row++);
            for (Holding holding : account.holdings) {
                Money value = holding.value().toMoney(RoundingMode.HALF_EVEN);
                gridPane.add(new Text(holding.investment.name), 0, row);
                gridPane.add(new Text(quantityFormatter.format(holding.quantity)), 1, row);
                gridPane.add(new Text(currencyFormatter.print(value)), 2, row);
                row++;
            }
        }
    }

    @Subscribe
    public void onEvent(AccountDAO.Event event) {
        rebuildAccounts();
    }
}
