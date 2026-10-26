package academy.tochkavhoda.competition.model;


public class Vote {
    private final String expertId;
    private final String applicationId;
    private final int score;

    public Vote(String expertId, String applicationId, int score) {
        this.expertId = expertId;
        this.applicationId = applicationId;
        this.score = score;
    }

    public String getExpertId() {
        return expertId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public int getScore() {
        return score;
    }
}
