package academy.tochkavhoda.competition.dto.request;

import java.util.List;

public class DirectionsRequest {
    private List<String> directions;

    public DirectionsRequest() {
    }

    public DirectionsRequest(List<String> directions) {
        this.directions = directions;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }
}
