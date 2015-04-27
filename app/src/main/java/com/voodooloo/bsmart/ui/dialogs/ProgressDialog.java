package com.voodooloo.bsmart.ui.dialogs;

import com.voodooloo.bsmart.utils.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import javax.inject.Inject;

public class ProgressDialog implements Controller {
    @FXML ProgressBar progressBar;

    Alert alert;

    @Inject
    public ProgressDialog() {
    }

    @FXML
    public void initialize() {
    }

    public void show(Node view) {
        if (alert != null) {
            return;
        }

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert = new Alert(Alert.AlertType.NONE);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContent(view);

        //JavaFX requires a cancel button in order to close a dialog, so let's add one and hide it
        alert.getButtonTypes().setAll(cancelButtonType);
        alert.getDialogPane().lookupButton(cancelButtonType).setVisible(false);

        alert.show();
    }

    public void hide() {
        if (alert != null) {
            alert.close();
        }
    }
}
