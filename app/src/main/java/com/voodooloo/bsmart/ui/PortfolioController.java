package com.voodooloo.bsmart.ui;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioData;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PortfolioController implements Controller {
    final App app;
    final PortfolioData portfolioData;
    final Portfolio portfolio;

    BorderPane rootBorderPane;

    public PortfolioController(App app, PortfolioData portfolioData, Portfolio portfolio) {
        this.app = app;
        this.portfolioData = portfolioData;
        this.portfolio = portfolio;
    }

    public void load() {
        app.bus().register(this);

        Text title = new Text(portfolio.name);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(title);
    }

    @Override
    public Node node() {
        return rootBorderPane;
    }

    @Override
    public void unload() {
        app.bus().unregister(this);
    }
}
