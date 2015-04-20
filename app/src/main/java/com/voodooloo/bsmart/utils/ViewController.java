package com.voodooloo.bsmart.utils;

import javafx.scene.Node;

public final class ViewController<V extends Node, C extends Controller> {
    final V view;
    public final C controller;

    public ViewController(V view, C controller) {
        this.view = view;
        this.controller = controller;
    }

    public V transition(ViewController<V, ?> from) {
        if (from != null) {
            from.controller.onHide();
        }

        controller.onShow();
        return view;
    }
}
