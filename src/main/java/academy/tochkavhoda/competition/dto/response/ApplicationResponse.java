package academy.tochkavhoda.competition.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ApplicationResponse {
    private String id;
    private String participantId;
    private String title;
    private String description;
    private List<String> directions;
    private BigDecimal amount;
    private BigDecimal averageScore;
    private int voteCount;
    private Integer myScore;

    public ApplicationResponse() {
    }

    public ApplicationResponse(String id, String participantId, String title, String description, List<String> directions, BigDecimal amount, BigDecimal averageScore, int voteCount, Integer myScore) {
        this.id = id;
        this.participantId = participantId;
        this.title = title;
        this.description = description;
        this.directions = directions;
        this.amount = amount;
        this.averageScore = averageScore;
        this.voteCount = voteCount;
        this.myScore = myScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getMyScore() {
        return myScore;
    }

    public void setMyScore(Integer myScore) {
        this.myScore = myScore;
    }
}
