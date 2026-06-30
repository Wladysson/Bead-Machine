package com.beadmachine.reconciliation.api.domain.divergence.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public final class DivergenceId implements Serializable {

    private final UUID value;

    private DivergenceId(UUID value) {

        if (value == null) {
            throw new IllegalArgumentException("Divergence identifier cannot be null.");
        }

        this.value = value;
    }

    public static DivergenceId generate() {
        return new DivergenceId(UUID.randomUUID());
    }

    public static DivergenceId from(UUID value) {
        return new DivergenceId(value);
    }

    public static DivergenceId from(String value) {
        return new DivergenceId(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof DivergenceId other)) {
            return false;
        }

        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}