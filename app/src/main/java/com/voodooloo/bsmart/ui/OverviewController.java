package com.voodooloo.bsmart.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;
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
    final PortfolioData portfolioData;

    BorderPane rootBorderPane;
    GridPane gridPane;

    @Inject
    public OverviewController(App app, PortfolioData portfolioData) {
        this.app = app;
        this.portfolioData = portfolioData;
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

        gridPane = new GridPane();

        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(header);
        rootBorderPane.setCenter(gridPane);
    }

    void rebuildGrid() {
        gridPane.getChildren().removeAll();
        gridPane.add(new Text("Name"), 0, 0);
        gridPane.add(new Text("Total"), 1, 0);

        int i;
        ImmutableList<Portfolio> portfolios = portfolioData.findAll();
        for (i = 0; i < portfolios.size(); i++ ) {
            Portfolio portfolio = portfolios.get(i);
            gridPane.add(new Text(portfolio.name), 0, i + 1);
            gridPane.add(new Text("$100"), 1, i + 1);
        }

        gridPane.add(new Text("Grand Total"), 0, i + 1);
        gridPane.add(new Text("$200"), 1, i + 1);
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
            portfolioData.insert(builder.build());
        }
    }

    @Subscribe
    public void onEvent(PortfolioData.Event event) {
        rebuildGrid();
    }
}
