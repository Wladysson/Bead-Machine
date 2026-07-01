package com.beadmachine.reconciliation.api.domain.execution.model;

import java.time.Instant;
import java.util.Objects;

public final class StreamLagSnapshot {

    private final String consumerGroup;
    private final String topic;
    private final int partition;
    private final long currentOffset;
    private final long endOffset;
    private final long lag;
    private final Instant capturedAt;
    private StreamLagSnapshot(
            String consumerGroup,
            String topic,
            int partition,
            long currentOffset,
            long endOffset,
            Instant capturedAt
    ) {

        this.consumerGroup = Objects.requireNonNull(consumerGroup);
        this.topic = Objects.requireNonNull(topic);

        if (partition < 0) {
            throw new IllegalArgumentException("Partition cannot be negative.");
        }

        this.partition = partition;
        this.currentOffset = currentOffset;
        this.endOffset = endOffset;
        this.lag = Math.max(endOffset - currentOffset, 0);
        this.capturedAt = Objects.requireNonNull(capturedAt);
    }

    public static StreamLagSnapshot of(
            String consumerGroup,
            String topic,
            int partition,
            long currentOffset,
            long endOffset,
            Instant capturedAt
    ) {

        return new StreamLagSnapshot(
                consumerGroup,
                topic,
                partition,
                currentOffset,
                endOffset,
                capturedAt
        );
    }

    public boolean isHealthy() {
        return lag < 100;
    }

    public boolean requiresAttention() {
        return lag >= 100;
    }

    public boolean isCritical() {
        return lag >= 5_000;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }

    public long getCurrentOffset() {
        return currentOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public long getLag() {
        return lag;
    }

    public Instant getCapturedAt() {
        return capturedAt;
    }

}