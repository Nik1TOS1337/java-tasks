package academy.tochkavhoda.competition.model;


public class GrantResult {
    private final Application application;
    private final long scoreSum;
    private final int voteCount;
    private final boolean funded;

    public GrantResult(Application application, long scoreSum, int voteCount, boolean funded) {
        this.application = application;
        this.scoreSum = scoreSum;
        this.voteCount = voteCount;
        this.funded = funded;
    }

    public Application getApplication() {
        return application;
    }

    public long getScoreSum() {
        return scoreSum;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean getFunded() {
        return funded;
    }
}
