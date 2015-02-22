package com.voodooloo.bsmart.ui.investments;

import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.investments.Investment;
import com.voodooloo.bsmart.ui.Controller;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.inject.Inject;

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

        for (Account account : accountDAO.findAll()) {
            GridPane gridPane = new GridPane();
            accountsBox.getChildren().add(gridPane);

            int row = 0;
            gridPane.add(new Text(account.name), 0, row++, 1, 1);

            gridPane.add(new Text("Funds"), 0, row++, 1, 1);
            for (Investment investment : account.investments) {
                gridPane.add(new Text("" + investment.quantity), 0, row++);
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
