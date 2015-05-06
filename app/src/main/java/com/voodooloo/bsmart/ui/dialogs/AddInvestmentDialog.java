package com.voodooloo.bsmart.ui.dialogs;

import com.google.common.eventbus.EventBus;
import com.voodooloo.bsmart.investments.InvestmentDAO;
import com.voodooloo.bsmart.investments.InvestmentType;
import com.voodooloo.bsmart.investments.MutualFund;
import com.voodooloo.bsmart.net.MutualFundProvider;
import com.voodooloo.bsmart.ui.utils.SimpleStringConverter;
import com.voodooloo.bsmart.utils.Controller;
import com.voodooloo.bsmart.utils.FXMLProvider;
import com.voodooloo.bsmart.utils.ForegroundExecutor;
import javafx.application.Platform;
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

import javax.inject.Inject;
import java.util.Optional;

public class AddInvestmentDialog implements Controller {
    final InvestmentDAO investmentDAO;
    final ForegroundExecutor foregroundExecutor;
    final FXMLProvider fxmlProvider;
    final MutualFundProvider mutualFundProvider;

    @FXML ComboBox<InvestmentType> typeBox;
    @FXML TextField symbolField;

    @Inject
    public AddInvestmentDialog(DSLContext context, EventBus bus, ForegroundExecutor foregroundExecutor, FXMLProvider fxmlProvider, MutualFundProvider mutualFundProvider) {
        this.foregroundExecutor = foregroundExecutor;
        this.fxmlProvider = fxmlProvider;
        this.mutualFundProvider = mutualFundProvider;

        investmentDAO = new InvestmentDAO(context, bus);
    }

    @FXML
    public void initialize() {
        typeBox.setConverter(new SimpleStringConverter<>(type -> type.name));
        typeBox.setItems(FXCollections.observableArrayList(investmentDAO.findTypes()));
    }

    public void show(Node view) {
        show(view, null);
    }

    void show(Node view, String message) {
        ButtonType saveButtonType = new ButtonType("Save");
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("New Investment");
        alert.setHeaderText(message);
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

        alert.showAndWait()
             .filter(result -> result == saveButtonType)
             .ifPresent(result -> foregroundExecutor.execute(() -> {
                 Optional<MutualFund> optional = mutualFundProvider.get(symbolField.getText());
                 if (optional.isPresent()) {
                     investmentDAO.insert(optional.get());
                 } else {
                     Platform.runLater(() -> show(view, "Couldn't find symbol"));
                 }
             }));
    }
}
