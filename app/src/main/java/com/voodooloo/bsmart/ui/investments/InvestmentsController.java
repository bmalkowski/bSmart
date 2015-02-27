package com.voodooloo.bsmart.ui.investments;

import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.investments.FundHolding;
import com.voodooloo.bsmart.ui.Controller;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.joda.money.Money;
import org.joda.money.format.*;

import javax.inject.Inject;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class InvestmentsController implements Controller {
    final App app;
    final AccountDAO accountDAO;

    BorderPane rootBorderPane;
    VBox accountsBox;

    @Inject
    public InvestmentsController(App app, AccountDAO accountDAO) {
        this.app = app;
        this.accountDAO = accountDAO;
    }

    public void load() {
        app.bus().register(this);

        accountsBox = new VBox();
        rebuildAccounts();

        rootBorderPane = new BorderPane();
        rootBorderPane.setCenter(accountsBox);
    }

    void rebuildAccounts() {
        accountsBox.getChildren().clear();

        NumberFormat quantityFormatter = NumberFormat.getNumberInstance();
        MoneyFormatter currencyFormatter = new MoneyFormatterBuilder().appendCurrencySymbolLocalized()
                                                                      .appendAmount(MoneyAmountStyle.LOCALIZED_GROUPING)
                                                                      .toFormatter();
        for (Account account : accountDAO.findAll()) {
            GridPane gridPane = new GridPane();
            accountsBox.getChildren().add(gridPane);

            int row = 0;
            gridPane.add(new Text(account.name), 0, row++);

            gridPane.add(new Text("Funds"), 0, row++);
            gridPane.add(new Text("Name"), 0, row);
            gridPane.add(new Text("Quantity"), 1, row);
            gridPane.add(new Text("Value"), 2, row++);
            for (FundHolding fundHolding : account.fundHoldings) {
                Money value = fundHolding.value().toMoney(RoundingMode.HALF_EVEN);
                gridPane.add(new Text(fundHolding.fund.name), 0, row);
                gridPane.add(new Text(quantityFormatter.format(fundHolding.quantity)), 1, row);
                gridPane.add(new Text(currencyFormatter.print(value)), 2, row);
              row++;
            }
        }
    }

    @Override
    public Node node() {
        return rootBorderPane;
    }

    @Override
    public void unload() {
        app.bus().unregister(this);
    }

    void onAdd(ActionEvent event) {
        new InvestmentFactory(app, accountDAO).showNewDialog();
    }

    @Subscribe
    public void onEvent(AccountDAO.Event event) {
        rebuildAccounts();
    }
}
