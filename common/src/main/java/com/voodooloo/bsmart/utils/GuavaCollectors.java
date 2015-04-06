package com.voodooloo.bsmart.utils;

import com.google.common.collect.ImmutableList;

import java.util.stream.Collector;

public final class GuavaCollectors {
    public static <T> Collector<T, ?, ImmutableList<T>> toImmutableList() {
        return Collector.of(ImmutableList.Builder::new, ImmutableList.Builder::add,
                            (l, r) -> l.addAll(r.build()), ImmutableList.Builder<T>::build);
    }
}