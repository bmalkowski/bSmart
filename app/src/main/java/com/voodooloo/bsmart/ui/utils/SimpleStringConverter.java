package com.voodooloo.bsmart.ui.utils;

import javafx.util.StringConverter;

public class SimpleStringConverter<T> extends StringConverter<T> {
    final Lookup<T> lookup;

    public SimpleStringConverter(Lookup<T> lookup) {
        this.lookup = lookup;
    }

    @Override
    public String toString(T object) {
        return lookup.get(object);
    }

    @Override
    public T fromString(String string) {
        return null;
    }

    public interface Lookup<T> {
        String get(T t);
    }
}
