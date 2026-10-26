package academy.tochkavhoda.competition.dto.request;


public class IdRequest {
    private String applicationId;

    public IdRequest() {
    }

    public IdRequest(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
