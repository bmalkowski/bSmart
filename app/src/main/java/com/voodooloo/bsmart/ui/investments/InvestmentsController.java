package com.voodooloo.bsmart.ui.investments;

import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.investments.Fund;
import com.voodooloo.bsmart.ui.Controller;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogAction;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

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

        Text title = new Text("Investments");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Button addButton = new Button("Add new account");
        AwesomeDude.setIcon(addButton, AwesomeIcon.PLUS);
        addButton.setOnAction(this::onAdd);

        Region flexibleSpace = new Region();
        HBox.setHgrow(flexibleSpace, Priority.ALWAYS);
        HBox header = new HBox(title, flexibleSpace, addButton);

        accountsBox = new VBox();
        rebuildAccounts();

        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(header);
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
            for (Fund fund : account.funds) {
                gridPane.add(new Text(fund.symbol), 0, row++);
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
        Action saveAction = new DialogAction("Save", ButtonBar.ButtonType.OK_DONE);
        ValidationSupport validationSupport = new ValidationSupport();
        Account.Builder builder = new Account.Builder();

        Text nameLabel = new Text("Name");
        TextField nameInput = new TextField();
        nameInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!validationSupport.isInvalid()) {
                    builder.name(newValue);
                }
            }
        });

        validationSupport.registerValidator(nameInput, Validator.createEmptyValidator("Name is required"));
        validationSupport.invalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                saveAction.setDisabled(newValue);
            }
        });

        GridPane gridPane = new GridPane();
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameInput, 1, 0);
        gridPane.getChildren().addAll(nameLabel, nameInput);

        final Dialog dialog = new Dialog(null, "New Portfolio", false);
        dialog.getStyleClass().add(Dialog.STYLE_CLASS_NATIVE);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(gridPane);
        dialog.getActions().setAll(saveAction, Dialog.ACTION_CANCEL);

        Action response = dialog.show();
        if (response == saveAction) {
            accountDAO.insert(builder.build());
        }
    }

    @Subscribe
    public void onEvent(AccountDAO.Event event) {
        rebuildAccounts();
    }
}
