package com.voodooloo.bsmart;

import com.voodooloo.bsmart.investments.PortfolioData;
import dagger.Module;

@Module(injects = {
        PortfolioData.class,
}, complete = false)
public class DataModule {
}

