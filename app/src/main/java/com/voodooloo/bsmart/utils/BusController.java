package com.voodooloo.bsmart.utils;

import com.google.common.eventbus.EventBus;

public class BusController implements Controller {
    final EventBus bus;

    public BusController(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void onShow() {
        bus.register(this);
    }

    @Override
    public void onHide() {
        bus.unregister(this);
    }

    public void post(Object event) {
        bus.post(event);
    }
}
