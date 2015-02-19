package com.voodooloo.bsmart.ui;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioData;

import javax.inject.Inject;

public class ControllerFactory {
    final App app;
    final PortfolioData portfolioData;

    @Inject
    public ControllerFactory(App app, PortfolioData portfolioData) {
        this.app = app;
        this.portfolioData = portfolioData;
    }

    public OverviewController overviewController() {
        return new OverviewController(app, portfolioData);
    }

    public PortfolioController portfolioController(Portfolio portfolio) {
        return new PortfolioController(app, portfolioData, portfolio);
    }

}
