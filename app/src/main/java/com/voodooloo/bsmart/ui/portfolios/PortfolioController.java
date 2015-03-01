package com.voodooloo.bsmart.ui.portfolios;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PortfolioController {
    final App app;
    final PortfolioDAO portfolioDAO;
    final Portfolio portfolio;

    BorderPane rootBorderPane;

    public PortfolioController(App app, PortfolioDAO portfolioDAO, Portfolio portfolio) {
        this.app = app;
        this.portfolioDAO = portfolioDAO;
        this.portfolio = portfolio;
    }

    public void load() {
        app.bus().register(this);

        Text title = new Text(portfolio.name);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(title);
    }
}
