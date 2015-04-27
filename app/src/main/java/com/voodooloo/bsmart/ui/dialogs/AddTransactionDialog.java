package com.voodooloo.bsmart.ui.dialogs;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.investments.*;
import com.voodooloo.bsmart.ui.utils.Formatter;
import com.voodooloo.bsmart.ui.utils.SimpleStringConverter;
import com.voodooloo.bsmart.utils.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogAction;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.jooq.DSLContext;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddTransactionDialog implements Controller {
    final AccountDAO accountDAO;
    final InvestmentDAO investmentDAO;

    @FXML ComboBox<Account> accountBox;
    @FXML ComboBox<Investment> investmentBox;
    @FXML DatePicker tradeDatePicker;
    @FXML TextField reasonField;
    @FXML TextField priceField;
    @FXML TextField quantityField;

    @Inject
    public AddTransactionDialog(DSLContext context, EventBus bus) {
        accountDAO = new AccountDAO(context, bus);
        investmentDAO = new InvestmentDAO(context, bus);
    }

    @FXML
    public void initialize() {
        accountBox.setConverter(new SimpleStringConverter<>(account -> account.name));
        accountBox.setItems(FXCollections.observableArrayList(accountDAO.findAll()));

        investmentBox.setConverter(new SimpleStringConverter<>(investment -> investment.name + " (" +  investment.symbol + ")"));
        investmentBox.setItems(FXCollections.observableArrayList(investmentDAO.findAll()));
        investmentBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            priceField.setText(newValue != null ? new Formatter().formatAsQuantity(newValue.price.getAmount()) : null);
        });

        tradeDatePicker.setValue(LocalDate.now());

        reasonField.setText("asdf");
    }

    public void show(Node view) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(accountBox, Validator.createEmptyValidator("Account is required"));
        validationSupport.registerValidator(investmentBox, Validator.createEmptyValidator("Investment is required"));
        validationSupport.registerValidator(tradeDatePicker, Validator.createEmptyValidator("Trade date is required"));
        validationSupport.registerValidator(reasonField, Validator.createEmptyValidator("Reason is required"));
        validationSupport.registerValidator(priceField, Validator.createEmptyValidator("Price is required"));
        validationSupport.registerValidator(quantityField, Validator.createEmptyValidator("Quantity is required"));

        Action saveAction = new DialogAction("Save", ButtonBar.ButtonType.OK_DONE);
        validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
            saveAction.setDisabled(newValue);
        });

        final Dialog dialog = new Dialog(null, "New Transaction", false);
        dialog.getStyleClass().add(Dialog.STYLE_CLASS_NATIVE);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(view);
        dialog.getActions().setAll(saveAction, Dialog.ACTION_CANCEL);

        Action response = dialog.show();
        if (response == saveAction) {
            Transaction.Builder builder = new Transaction.Builder();
            builder.investment(investmentBox.getValue())
                   .tradeDate(tradeDatePicker.getValue())
                   .reason(reasonField.getText())
                   .price(BigMoney.of(CurrencyUnit.USD, new BigDecimal(priceField.getText())))
                   .quantity(new BigDecimal(quantityField.getText()));

            accountDAO.add(accountBox.getValue(), builder.build());
        }
    }
}
