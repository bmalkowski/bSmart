package com.voodooloo.bsmart.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogAction;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.jooq.DSLContext;

public class AppController implements Controller {
    final DSLContext context;
    final EventBus bus;

    final BorderPane rootBorderPane;
    final TabPane tabPane;

    public AppController(DSLContext context, EventBus bus) {
        this.context = context;
        this.bus = bus;
        bus.register(this);

        Button addButton = new Button();
        AwesomeDude.setIcon(addButton, AwesomeIcon.PLUS);
        addButton.setOnAction(this::onAdd);

        Tab overviewTab = new Tab();
        overviewTab.setText("Overview");
        overviewTab.setContent(new Text("overview content"));

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(overviewTab);

        Region flexibleSpace = new Pane();
        HBox.setHgrow(flexibleSpace, Priority.ALWAYS);

        HBox navBar = new HBox(flexibleSpace, addButton);
        StackPane.setAlignment(navBar, Pos.CENTER_RIGHT);

        rootBorderPane = new BorderPane();
        rootBorderPane.setCenter(new StackPane(tabPane, navBar));
    }

    @Override
    public Node node() {
        return rootBorderPane;
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
        if (response == saveAction)
        {
            new PortfolioDAO(context, bus).insert(builder.build());
        }
    }

    @Subscribe
    public void onEvent(PortfolioDAO.Event event) {
        ObservableList<Tab> tabs = tabPane.getTabs();
        if (tabs.size() > 1)
        {
            tabs.remove(1, tabs.size());
        }

        ImmutableList<Portfolio> portfolios = new PortfolioDAO(context, bus).findAll();
        for (Portfolio porfolio : portfolios) {
            Tab tab = new Tab();
            tab.setText(porfolio.name);
            tab.setContent(new Text(porfolio.name + " content"));
            tab.setUserData(porfolio);
            tabs.add(tab);
        }
    }
}
