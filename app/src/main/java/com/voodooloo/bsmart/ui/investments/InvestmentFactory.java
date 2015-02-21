package com.voodooloo.bsmart.ui.investments;

import com.voodooloo.bsmart.App;
import com.voodooloo.bsmart.investments.AccountDAO;

import javax.inject.Inject;

public class InvestmentFactory {
    final App app;
    final AccountDAO accountDAO;

    @Inject
    public InvestmentFactory(App app, AccountDAO accountDAO) {
        this.app = app;
        this.accountDAO = accountDAO;
    }

    public InvestmentsController investmentsController() {
        return new InvestmentsController(app, accountDAO);
    }
}
