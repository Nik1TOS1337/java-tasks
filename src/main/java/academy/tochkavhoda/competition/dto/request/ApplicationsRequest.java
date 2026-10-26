package academy.tochkavhoda.competition.dto.request;

import java.util.List;

public class ApplicationsRequest {
    private List<ApplicationRequest> applications;

    public ApplicationsRequest() {
    }

    public ApplicationsRequest(List<ApplicationRequest> applications) {
        this.applications = applications;
    }

    public List<ApplicationRequest> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationRequest> applications) {
        this.applications = applications;
    }
}
