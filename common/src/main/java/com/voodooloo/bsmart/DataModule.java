package com.voodooloo.bsmart;

import com.voodooloo.bsmart.investments.AccountDAO;
import com.voodooloo.bsmart.investments.PortfolioDAO;
import dagger.Module;

@Module(injects = {
        PortfolioDAO.class,
        AccountDAO.class,
}, complete = false)
public class DataModule {
}

