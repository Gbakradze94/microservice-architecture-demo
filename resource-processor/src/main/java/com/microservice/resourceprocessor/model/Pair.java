package com.microservice.resourceprocessor.model;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Pair<S, T> {
    private final S source;
    private final T target;

    private Pair(S source, T target) {
        Assert.notNull(source, "Source should not be null");
        Assert.notNull(target, "Source should not be null");

        this.source = source;
        this.target = target;
    }


    public static <S, T> Pair<S, T> of(S source, T target) {
        return new Pair<>(source, target);
    }

    public static <S, T> Collector<Pair<S, T>, ?, Map<S, T>> toMap() {
        return Collectors.toMap(Pair::getSource, Pair::getTarget);
    }


    public S getSource() {
        return source;
    }

    public T getTarget() {
        return target;
    }

    @Override
    public boolean equals(@Nullable Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!ObjectUtils.nullSafeEquals(source, pair.source)) {
            return false;
        }

        return ObjectUtils.nullSafeEquals(target, pair.target);
    }

    @Override
    public int hashCode() {
        int result = ObjectUtils.nullSafeHashCode(source);
        result = 31 * result + ObjectUtils.nullSafeHashCode(target);
        return result;
    }
    @Override
    public String toString() {
        return String.format("%s->%s", this.source, this.target);
    }
}
