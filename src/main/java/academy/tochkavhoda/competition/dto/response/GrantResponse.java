package academy.tochkavhoda.competition.dto.response;


public class GrantResponse {
    private ApplicationResponse application;
    private boolean funded;

    public GrantResponse() {
    }

    public GrantResponse(ApplicationResponse application, boolean funded) {
        this.application = application;
        this.funded = funded;
    }

    public ApplicationResponse getApplication() {
        return application;
    }

    public void setApplication(ApplicationResponse application) {
        this.application = application;
    }

    public boolean getFunded() {
        return funded;
    }

    public void setFunded(boolean funded) {
        this.funded = funded;
    }
}
