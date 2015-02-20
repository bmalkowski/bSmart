package com.voodooloo.bsmart.ui;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.AccountData;
import com.voodooloo.bsmart.investments.Portfolio;
import com.voodooloo.bsmart.investments.PortfolioData;

import javax.inject.Inject;

public class ControllerFactory {
    final App app;
    final PortfolioData portfolioData;
    final AccountData accountData;

    @Inject
    public ControllerFactory(App app, PortfolioData portfolioData, AccountData accountData) {
        this.app = app;
        this.portfolioData = portfolioData;
        this.accountData = accountData;
    }

    public OverviewController overviewController() {
        return new OverviewController(app, portfolioData);
    }

    public InvestmentsController investmentsController() {
        return new InvestmentsController(app, accountData);
    }

    public PortfolioController portfolioController(Portfolio portfolio) {
        return new PortfolioController(app, portfolioData, portfolio);
    }

}