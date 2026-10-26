package academy.tochkavhoda.competition.model;

import java.math.BigDecimal;
import java.util.Set;

public class Application {
    private final String id;
    private final String participantId;
    private final String title;
    private final String description;
    private final Set<String> directions;
    private final BigDecimal amount;

    public Application(String id, String participantId, String title, String description, Set<String> directions, BigDecimal amount) {
        this.id = id;
        this.participantId = participantId;
        this.title = title;
        this.description = description;
        this.directions = Set.copyOf(directions);
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getParticipantId() {
        return participantId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getDirections() {
        return directions;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
