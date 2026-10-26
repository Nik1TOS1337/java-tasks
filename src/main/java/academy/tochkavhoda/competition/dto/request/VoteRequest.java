package academy.tochkavhoda.competition.dto.request;


public class VoteRequest {
    private String applicationId;
    private Integer score;

    public VoteRequest() {
    }

    public VoteRequest(String applicationId, Integer score) {
        this.applicationId = applicationId;
        this.score = score;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
