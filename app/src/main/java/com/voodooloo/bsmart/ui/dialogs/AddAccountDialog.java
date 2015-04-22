package com.voodooloo.bsmart.ui.dialogs;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.investments.Account;
import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.investments.Firm;
import com.voodooloo.bsmart.investments.FirmDAO;
import com.voodooloo.bsmart.ui.utils.SimpleStringConverter;
import com.voodooloo.bsmart.utils.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogAction;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.jooq.DSLContext;

import javax.inject.Inject;

public class AddAccountDialog implements Controller {
    final AccountDAO accountDAO;
    final FirmDAO firmDAO;

    @FXML TextField nameField;
    @FXML ComboBox<Firm> firmBox;

    @Inject
    public AddAccountDialog(DSLContext context, EventBus bus) {
        accountDAO = new AccountDAO(context, bus);
        firmDAO = new FirmDAO(context);
    }

    @FXML
    public void initialize() {
        firmBox.setConverter(new SimpleStringConverter<>(firm -> firm.name));
        firmBox.setItems(FXCollections.observableArrayList(firmDAO.findAll()));
    }

    public void show(Node view) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(nameField, Validator.createEmptyValidator("Name is required"));
        validationSupport.registerValidator(firmBox, Validator.createEmptyValidator("Firm is required"));

        Action saveAction = new DialogAction("Save", ButtonBar.ButtonType.OK_DONE);
        validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
            saveAction.setDisabled(newValue);
        });

        final Dialog dialog = new Dialog(null, "New Account", false);
        dialog.getStyleClass().add(Dialog.STYLE_CLASS_NATIVE);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(view);
        dialog.getActions().setAll(saveAction, Dialog.ACTION_CANCEL);

        Action response = dialog.show();
        if (response == saveAction) {
            accountDAO.insert(new Account.Builder().name(nameField.getText())
                                                   .firm(firmBox.getValue())
                                                   .build());
        }
    }
}
