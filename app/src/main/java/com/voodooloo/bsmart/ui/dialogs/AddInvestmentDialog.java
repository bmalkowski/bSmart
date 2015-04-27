package com.voodooloo.bsmart.ui.dialogs;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.investments.Investment;
import com.voodooloo.bsmart.investments.InvestmentDAO;
import com.voodooloo.bsmart.investments.InvestmentType;
import com.voodooloo.bsmart.net.MutualFundProvider;
import com.voodooloo.bsmart.ui.utils.SimpleStringConverter;
import com.voodooloo.bsmart.utils.Controller;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ForegroundExecutor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.jooq.DSLContext;
import org.pmw.tinylog.Logger;

import javax.inject.Inject;
import java.util.Optional;

public class AddInvestmentDialog implements Controller {
    final InvestmentDAO investmentDAO;
    final ForegroundExecutor foregroundExecutor;
    final FXMLProvider fxmlProvider;

    @FXML ComboBox<InvestmentType> typeBox;
    @FXML TextField symbolField;

    @Inject
    public AddInvestmentDialog(DSLContext context, EventBus bus, ForegroundExecutor foregroundExecutor, FXMLProvider fxmlProvider) {
        this.foregroundExecutor = foregroundExecutor;
        investmentDAO = new InvestmentDAO(context);
        this.fxmlProvider = fxmlProvider;
    }

    @FXML
    public void initialize() {
        typeBox.setConverter(new SimpleStringConverter<>(type -> type.name));
        typeBox.setItems(FXCollections.observableArrayList(investmentDAO.findTypes()));
    }

    public void show(Node view) {
        ButtonType saveButtonType = new ButtonType("Save");
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("New Investment");
        alert.getDialogPane().setContent(view);
        alert.getButtonTypes().setAll(saveButtonType, cancelButtonType);
        alert.getDialogPane().getScene().setFill(Color.DARKRED);

        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(symbolField, Validator.createEmptyValidator("Symbol is required"));
        validationSupport.registerValidator(typeBox, Validator.createEmptyValidator("Type is required"));

        Node saveButton = alert.getDialogPane().lookupButton(saveButtonType);
        validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue);
        });

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            foregroundExecutor.execute(() -> {
                Optional<Investment> investmentOptional = new MutualFundProvider().get(symbolField.getText());
                investmentOptional.ifPresent(investment1 -> {
                    Logger.info("woo");
                    investmentDAO.insert
                });
            });
        }
    }

    /*
    public void show(Node view) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(symbolField, Validator.createEmptyValidator("Symbol is required"));
        validationSupport.registerValidator(typeBox, Validator.createEmptyValidator("Type is required"));

        Action saveAction = new DialogAction("Save", ButtonBar.ButtonType.OK_DONE);
        validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> {
            saveAction.setDisabled(newValue);
        });
        Dialogs.create().showWorkerProgress();
        final Dialog dialog = new Dialog(null, "New Investment", false);
        dialog.getStyleClass().add(Dialog.STYLE_CLASS_NATIVE);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(view);
        dialog.getActions().setAll(saveAction, Dialog.ACTION_CANCEL);

        Action response = dialog.show();
        if (response == saveAction) {
            foregroundExecutor.execute(() -> {
//                progress.start();
                new MutualFundProvider().get(symbolField.getText());
//                progress.end();
            });
//            switch (typeBox.getValue().)
            //lookup
            //insert
            //take to an edit / details screen with categories?
            //find... bleh
//            investmentDAO.insert(typeBox.getValue(), symbolField.getText());
        }
    }*/
}
