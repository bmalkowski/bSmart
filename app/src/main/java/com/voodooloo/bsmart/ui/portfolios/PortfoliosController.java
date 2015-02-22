package com.voodooloo.bsmart.ui.portfolios;

import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import com.voodooloo.bsmart.ui.Controller;
import com.voodooloo.bsmart.ui.utils.SimpleValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class PortfoliosController implements Controller {
    final App app;
    final PortfolioDAO portfolioDAO;

    BorderPane rootBorderPane;
    TableView<Portfolio> portfolioTable;

    public PortfoliosController(App app, PortfolioDAO portfolioDAO) {
        this.app = app;
        this.portfolioDAO = portfolioDAO;
    }

    public void load() {
        app.bus().register(this);

        TableColumn<Portfolio, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new SimpleValueFactory<>(portfolio -> portfolio.name));

        TableColumn<Portfolio, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new SimpleValueFactory<>(portfolio -> portfolio.name));

        portfolioTable = new TableView<>();
        portfolioTable.getColumns().add(nameColumn);
        portfolioTable.getColumns().add(totalColumn);

        updatePortfolios();

        rootBorderPane = new BorderPane();
        rootBorderPane.setCenter(portfolioTable);
    }

    void updatePortfolios() {
        ObservableList<Portfolio> portfolios = FXCollections.observableArrayList(portfolioDAO.findAll());
        portfolioTable.setItems(portfolios);
    }

    @Override
    public Node node() {
        return rootBorderPane;
    }

    @Override
    public void unload() {
        app.bus().unregister(this);
    }

    @Subscribe
    public void onEvent(PortfolioDAO.Event event) {
        updatePortfolios();
    }
}
