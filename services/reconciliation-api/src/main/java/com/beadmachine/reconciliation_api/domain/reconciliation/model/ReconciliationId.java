package com.beadmachine.reconciliation.api.domain.reconciliation.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public final class ReconciliationId implements Serializable {

    private final UUID value;

    private ReconciliationId(UUID value) {

        if (value == null) {
            throw new IllegalArgumentException(
                    "Reconciliation id cannot be null."
            );
        }

        this.value = value;
    }

    public static ReconciliationId generate() {
        return new ReconciliationId(UUID.randomUUID());
    }

    public static ReconciliationId from(UUID value) {
        return new ReconciliationId(value);
    }

    public static ReconciliationId from(String value) {
        return new ReconciliationId(UUID.fromString(value));
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

        if (!(o instanceof ReconciliationId that)) {
            return false;
        }

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}