package academy.tochkavhoda.competition.model;

import java.math.BigDecimal;
import java.util.List;

public class CompetitionResult {
    private final List<GrantResult> applications;
    private final BigDecimal remainingFund;

    public CompetitionResult(List<GrantResult> applications, BigDecimal remainingFund) {
        this.applications = List.copyOf(applications);
        this.remainingFund = remainingFund;
    }

    public List<GrantResult> getApplications() {
        return applications;
    }

    public BigDecimal getRemainingFund() {
        return remainingFund;
    }
}
