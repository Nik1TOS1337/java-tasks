package academy.tochkavhoda.competition.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Settings {
    private final BigDecimal fund;
    private final BigDecimal threshold;
    private final Instant deadline;

    public Settings(BigDecimal fund, BigDecimal threshold, Instant deadline) {
        this.fund = fund;
        this.threshold = threshold;
        this.deadline = deadline;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public Instant getDeadline() {
        return deadline;
    }
}
