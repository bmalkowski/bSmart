package com.voodooloo.bsmart.utils;

import com.voodooloo.bsmart.ui.dialogs.ProgressDialog;
import javafx.application.Platform;
import javafx.scene.Node;

import javax.inject.Inject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ForegroundExecutor extends ThreadPoolExecutor {
    final ViewController<Node, ProgressDialog> alertViewController;

    @Inject
    public ForegroundExecutor(FXMLProvider fxmlProvider, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        alertViewController = fxmlProvider.load("dialogs/progress.fxml");
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        Platform.runLater(() -> alertViewController.controller.show(alertViewController.transition(null)));
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Platform.runLater(alertViewController.controller::hide);
    }
}
