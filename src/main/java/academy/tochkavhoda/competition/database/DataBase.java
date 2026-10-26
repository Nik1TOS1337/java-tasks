package academy.tochkavhoda.competition.database;

import academy.tochkavhoda.competition.model.*;
import academy.tochkavhoda.competition.exception.CompetitionException;
import java.util.*;

public class DataBase {
    private static final DataBase INSTANCE = new DataBase();
    private final Map<String, User> users = new LinkedHashMap<>();
    private final Map<String, String> sessions = new HashMap<>();
    private final Map<String, Application> applications = new LinkedHashMap<>();
    private final Map<String, Map<String, Vote>> votes = new HashMap<>();
    private Settings settings;
    private CompetitionResult result;

    private DataBase() {
    }

    public static DataBase getInstance() {
        return INSTANCE;
    }

    public void configure(Settings settings) {
        if (this.settings == null) {
            this.settings = settings;
        } else if (this.settings.getFund().compareTo(settings.getFund()) != 0
                || this.settings.getThreshold().compareTo(settings.getThreshold()) != 0
                || !this.settings.getDeadline().equals(settings.getDeadline())) {
            throw new IllegalArgumentException("Параметры конкурса уже заданы");
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public void insertUser(User user) {
        for (User existing : users.values()) {
            if (existing.getLogin().equals(user.getLogin())) {
                throw new CompetitionException("Логин уже занят");
            }
            if (user.getRole() == existing.getRole()) {
                if (user.getRole() == Role.PARTICIPANT
                        && existing.getCompany().equalsIgnoreCase(user.getCompany())) {
                    throw new CompetitionException("Фирма уже зарегистрирована");
                }
                if (user.getRole() == Role.EXPERT
                        && existing.getFirstName().equalsIgnoreCase(user.getFirstName())
                        && existing.getLastName().equalsIgnoreCase(user.getLastName())) {
                    throw new CompetitionException("Эксперт уже зарегистрирован");
                }
            }
        }
        users.put(user.getId(), user);
    }

    public User findByLogin(String login) {
        for (User user : users.values()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public User findByToken(String token) {
        return users.get(sessions.get(token));
    }

    public void addSession(String token, String userId) {
        sessions.values().removeIf(id -> id.equals(userId));
        sessions.put(token, userId);
    }

    public void removeSession(String token) {
        sessions.remove(token);
    }

    public void removeUser(String id) {
        List<String> ids = new ArrayList<>();
        for (Application application : applications.values()) {
            if (application.getParticipantId().equals(id)) {
                ids.add(application.getId());
            }
        }
        for (String applicationId : ids) {
            removeApplication(applicationId);
        }
        for (Map<String, Vote> values : votes.values()) {
            values.remove(id);
        }
        sessions.values().removeIf(userId -> userId.equals(id));
        users.remove(id);
    }

    public void insertApplications(List<Application> values) {
        for (Application application : values) {
            applications.put(application.getId(), application);
        }
    }

    public Application findApplication(String id) {
        return applications.get(id);
    }

    public List<Application> getApplications() {
        return new ArrayList<>(applications.values());
    }

    public void removeApplication(String id) {
        applications.remove(id);
        votes.remove(id);
    }

    public void saveVote(Vote vote) {
        votes.computeIfAbsent(vote.getApplicationId(), id -> new HashMap<>())
                .put(vote.getExpertId(), vote);
    }

    public void removeVote(String applicationId, String expertId) {
        Map<String, Vote> values = votes.get(applicationId);
        if (values == null || values.remove(expertId) == null) {
            throw new CompetitionException("Оценка не найдена");
        }
    }

    public List<Vote> getVotes(String applicationId) {
        Map<String, Vote> values = votes.get(applicationId);
        return values == null ? new ArrayList<>() : new ArrayList<>(values.values());
    }

    public CompetitionResult getResult() {
        return result;
    }

    public void saveResult(CompetitionResult result) {
        if (this.result == null) {
            this.result = result;
        }
    }

    public void clear() {
        users.clear();
        sessions.clear();
        applications.clear();
        votes.clear();
        result = null;
    }
}
