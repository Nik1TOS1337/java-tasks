package academy.tochkavhoda.competition.dto.response;

import java.util.List;

public class ApplicationsResponse {
    private List<ApplicationResponse> applications;

    public ApplicationsResponse() {
    }

    public ApplicationsResponse(List<ApplicationResponse> applications) {
        this.applications = applications;
    }

    public List<ApplicationResponse> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationResponse> applications) {
        this.applications = applications;
    }
}
