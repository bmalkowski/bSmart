package com.voodooloo.bsmart.utils;

public final class ViewController<V, C> {
    public final V view;
    public final C controller;

    public ViewController(V view, C controller) {
        this.view = view;
        this.controller = controller;
    }
}
