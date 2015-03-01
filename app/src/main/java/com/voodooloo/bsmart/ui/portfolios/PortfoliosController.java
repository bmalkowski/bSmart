package com.voodooloo.bsmart.ui.portfolios;

import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

public class PortfoliosController {
    final App app;
    final PortfolioDAO portfolioDAO;

    @FXML BorderPane root;
    @FXML TableView<Portfolio> portfolioTable;
    @FXML TableColumn<Portfolio, String> nameColumn;
    @FXML TableColumn<Portfolio, String> totalColumn;

    @Inject
    public PortfoliosController(App app, PortfolioDAO portfolioDAO) {
        this.app = app;
        this.portfolioDAO = portfolioDAO;
    }

    @FXML
    public void initialize() {
        app.bus().register(this);

        nameColumn.setCellValueFactory(new SimpleValueFactory<>(portfolio -> portfolio.name));
        totalColumn.setCellValueFactory(new SimpleValueFactory<>(portfolio -> portfolio.name));
        updatePortfolios();
    }

    void updatePortfolios() {
        ObservableList<Portfolio> portfolios = FXCollections.observableArrayList(portfolioDAO.findAll());
        portfolioTable.setItems(portfolios);
    }

    @Subscribe
    public void onEvent(PortfolioDAO.Event event) {
        updatePortfolios();
    }
}
