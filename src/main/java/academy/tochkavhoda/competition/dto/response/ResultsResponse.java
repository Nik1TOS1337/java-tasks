package academy.tochkavhoda.competition.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ResultsResponse {
    private List<GrantResponse> applications;
    private BigDecimal remainingFund;

    public ResultsResponse() {
    }

    public ResultsResponse(List<GrantResponse> applications, BigDecimal remainingFund) {
        this.applications = applications;
        this.remainingFund = remainingFund;
    }

    public List<GrantResponse> getApplications() {
        return applications;
    }

    public void setApplications(List<GrantResponse> applications) {
        this.applications = applications;
    }

    public BigDecimal getRemainingFund() {
        return remainingFund;
    }

    public void setRemainingFund(BigDecimal remainingFund) {
        this.remainingFund = remainingFund;
    }
}
