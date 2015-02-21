package com.voodooloo.bsmart.ui.portfolios;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.Subscribe;
import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import com.voodooloo.bsmart.ui.Controller;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PortfoliosController implements Controller {
    final App app;
    final PortfolioDAO portfolioDAO;

    BorderPane rootBorderPane;
    GridPane gridPane;

    public PortfoliosController(App app, PortfolioDAO portfolioDAO) {
        this.app = app;
        this.portfolioDAO = portfolioDAO;
    }

    public void load() {
        app.bus().register(this);

        Text title = new Text("Overview");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        gridPane = new GridPane();
        populateGrid();

        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(title);
        rootBorderPane.setCenter(gridPane);
    }

    void populateGrid() {
        gridPane.getChildren().clear();
        gridPane.add(new Text("Name"), 0, 0);
        gridPane.add(new Text("Total"), 1, 0);

        int i;
        ImmutableList<Portfolio> portfolios = portfolioDAO.findAll();
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

    @Subscribe
    public void onEvent(PortfolioDAO.Event event) {
        populateGrid();
    }
}
