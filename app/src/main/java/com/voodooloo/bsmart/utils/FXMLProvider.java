package com.voodooloo.bsmart.utils;

import com.google.common.io.Resources;
import dagger.ObjectGraph;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.pmw.tinylog.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

public class FXMLProvider {
    final ObjectGraph objectGraph;

    @Inject
    public FXMLProvider(final ObjectGraph objectGraph) {
        this.objectGraph = objectGraph;
    }

    public <V extends Node, C extends Controller> ViewController<V, C> load(String name) {
        return load(Resources.getResource(name));
    }

    public <V extends Node, C extends Controller> ViewController<V, C> load(URL url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        loader.setControllerFactory(this::buildController);

        try {
            return new ViewController<>(loader.load(url.openStream()), loader.getController());
        } catch (IOException e) {
            Logger.error(e);
            return null;
        }
    }

    Object buildController(Class<?> type) {
        return type == null ? null : objectGraph.get(type);
    }

}
