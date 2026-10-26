package academy.tochkavhoda.competition.dto.response;

import java.util.List;

public class IdsResponse {
    private List<String> applicationIds;

    public IdsResponse() {
    }

    public IdsResponse(List<String> applicationIds) {
        this.applicationIds = applicationIds;
    }

    public List<String> getApplicationIds() {
        return applicationIds;
    }

    public void setApplicationIds(List<String> applicationIds) {
        this.applicationIds = applicationIds;
    }
}
