package com.voodooloo.bsmart.ui.accounts;

import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.utils.FXMLProvider;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javax.inject.Inject;


public class AccountsController {
    final FXMLProvider fxmlProvider;
    final AccountDAO accountDAO;
    final Formatter formatter;

    @FXML HBox root;
    @FXML VBox list;
    @FXML VBox content;
    @FXML HBox options;

    @Inject
    public AccountsController(AccountDAO accountDAO, FXMLProvider fxmlProvider) {
        this.accountDAO = accountDAO;
        this.fxmlProvider = fxmlProvider;
        formatter = new Formatter();
    }

    @FXML
    public void initialize() {
        list.getChildren().clear();

        for (Account account : accountDAO.findAll()) {
            Text name = new Text(account.name);

            Region fill = new Region();
            HBox.setHgrow(fill, Priority.ALWAYS);

            Text value = new Text(formatter.formatAsCurrency(account.value()));
            value.setTextAlignment(TextAlignment.RIGHT);

            list.getChildren().add(new HBox(name, fill, value));
        }
    }
}
