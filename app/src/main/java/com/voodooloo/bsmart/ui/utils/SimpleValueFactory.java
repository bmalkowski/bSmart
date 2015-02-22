package com.voodooloo.bsmart.ui.utils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class SimpleValueFactory<S, T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    final Lookup<S, T> lookup;

    public SimpleValueFactory(Lookup<S, T> lookup) {
        this.lookup = lookup;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> data) {
        S value = data.getValue();
        if (value == null || lookup == null) {
            return null;
        }

        return new ReadOnlyObjectWrapper<>(lookup.get(value));
    }

    public interface Lookup<S, T> {
        public T get(S s);
    }
}
