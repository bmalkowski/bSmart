package com.voodooloo.bsmart.ui.portfolios;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.DialogAction;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javax.inject.Inject;

public class PortfolioFactory {
    final App app;
    final PortfolioDAO dao;

    @Inject
    public PortfolioFactory(App app, PortfolioDAO dao) {
        this.app = app;
        this.dao = dao;
    }

    public PortfoliosController portfoliosController() {
        return new PortfoliosController(app, dao);
    }

    public PortfolioController portfoliosController(Portfolio portfolio) {
        return new PortfolioController(app, dao, portfolio);
    }

    public void showNewDialog() {
        Action saveAction = new DialogAction("Save", ButtonBar.ButtonType.OK_DONE);
        ValidationSupport validationSupport = new ValidationSupport();
        Portfolio.Builder builder = new Portfolio.Builder();

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

        final org.controlsfx.dialog.Dialog dialog = new org.controlsfx.dialog.Dialog(null, "New Portfolio", false);
        dialog.getStyleClass().add(org.controlsfx.dialog.Dialog.STYLE_CLASS_NATIVE);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(gridPane);
        dialog.getActions().setAll(saveAction, org.controlsfx.dialog.Dialog.ACTION_CANCEL);

        Action response = dialog.show();
        if (response == saveAction) {
            dao.insert(builder.build());
        }
    }
}
