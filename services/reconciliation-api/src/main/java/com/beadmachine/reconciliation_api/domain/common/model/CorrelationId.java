package com.beadmachine.reconciliation.api.domain.common.model;

import java.util.Objects;
import java.util.UUID;

public final class CorrelationId {

    private final UUID value;

    private CorrelationId(UUID value) {

        if (value == null) {
            throw new IllegalArgumentException(
                    "CorrelationId cannot be null."
            );
        }

        this.value = value;
    }

    public static CorrelationId generate() {
        return new CorrelationId(UUID.randomUUID());
    }

    public static CorrelationId from(String value) {
        return new CorrelationId(UUID.fromString(value));
    }

    public static CorrelationId from(UUID value) {
        return new CorrelationId(value);
    }

    public UUID value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof CorrelationId that)) {
            return false;
        }

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}