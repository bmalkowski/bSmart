package com.voodooloo.bsmart.utils;

import dagger.ObjectGraph;
import javafx.fxml.FXMLLoader;
import org.pmw.tinylog.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

public class DaggerFXMLLoader {
    final ObjectGraph objectGraph;

    @Inject
    public DaggerFXMLLoader(final ObjectGraph objectGraph) {
        this.objectGraph = objectGraph;
    }

    public <T> T load(URL url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        loader.setControllerFactory(this::buildController);

        try {
            return loader.load(url.openStream());
        } catch (IOException e) {
            Logger.error(e);
            return null;
        }
    }

    Object buildController(Class<?> type) {
        return type == null ? null : objectGraph.get(type);
    }
}
