package academy.tochkavhoda.competition.dao;

import academy.tochkavhoda.competition.model.*;
import java.util.List;

public interface CompetitionDao {
    void configure(Settings settings);
    Settings getSettings();
    void insertUser(User user);
    User findByLogin(String login);
    User findByToken(String token);
    void addSession(String token, String userId);
    void removeSession(String token);
    void removeUser(String id);
    void insertApplications(List<Application> applications);
    Application findApplication(String id);
    List<Application> getApplications();
    void removeApplication(String id);
    void saveVote(Vote vote);
    void removeVote(String applicationId, String expertId);
    List<Vote> getVotes(String applicationId);
    CompetitionResult getResult();
    void saveResult(CompetitionResult result);
    void clear();
}
