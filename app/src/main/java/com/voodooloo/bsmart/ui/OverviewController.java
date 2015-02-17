package com.voodooloo.bsmart.ui;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioData;
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

public class OverviewController implements Controller {
    final App app;
    final PortfolioData portfolioDAO;

    BorderPane rootBorderPane;

    @Inject
    public OverviewController(App app) {
        this.app = app;
        portfolioDAO = app.get(PortfolioData.class);
    }

    public void load() {
        app.bus().register(this);

        Text title = new Text("Overview");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Button addButton = new Button();
        AwesomeDude.setIcon(addButton, AwesomeIcon.PLUS);
        addButton.setOnAction(this::onAdd);

        Region flexibleSpace = new Region();
        HBox.setHgrow(flexibleSpace, Priority.ALWAYS);
        HBox header = new HBox(title, flexibleSpace, addButton);

//        summary = new Text();
//        VBox info = new VBox(title, summary);
//
//        total = new Text("$123,000");
//        total.setFont(Font.font("Arial", 20));
//
//        Pane flexibleSpace = new Pane();
//        HBox.setHgrow(flexibleSpace, Priority.ALWAYS);
//        HBox header = new HBox(info, flexibleSpace, total);
//
//        TableColumn<Portfolio, String> nameColumn = new TableColumn<>("Name");
//        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().name));
//
//        TableView<Portfolio> table = new TableView<>();
//        table.getColumns().addAll(nameColumn);
//        table.setItems(portfolios);
//
//        vBox = new VBox(header, table);

        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(header);
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

        final Dialog dialog = new Dialog(null, "New Portfolio", false);
        dialog.getStyleClass().add(Dialog.STYLE_CLASS_NATIVE);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(gridPane);
        dialog.getActions().setAll(saveAction, Dialog.ACTION_CANCEL);

        Action response = dialog.show();
        if (response == saveAction) {
            portfolioDAO.insert(builder.build());
        }
    }
}
