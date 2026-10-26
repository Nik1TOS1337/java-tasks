package academy.tochkavhoda.competition.daoimpl;

import academy.tochkavhoda.competition.dao.CompetitionDao;
import academy.tochkavhoda.competition.database.DataBase;
import academy.tochkavhoda.competition.model.*;
import java.util.List;

public class CompetitionDaoImpl implements CompetitionDao {
    private final DataBase database = DataBase.getInstance();

    @Override
    public void configure(Settings settings) {
        database.configure(settings);
    }

    @Override
    public Settings getSettings() {
        return database.getSettings();
    }

    @Override
    public void insertUser(User user) {
        database.insertUser(user);
    }

    @Override
    public User findByLogin(String login) {
        return database.findByLogin(login);
    }

    @Override
    public User findByToken(String token) {
        return database.findByToken(token);
    }

    @Override
    public void addSession(String token, String userId) {
        database.addSession(token, userId);
    }

    @Override
    public void removeSession(String token) {
        database.removeSession(token);
    }

    @Override
    public void removeUser(String id) {
        database.removeUser(id);
    }

    @Override
    public void insertApplications(List<Application> applications) {
        database.insertApplications(applications);
    }

    @Override
    public Application findApplication(String id) {
        return database.findApplication(id);
    }

    @Override
    public List<Application> getApplications() {
        return database.getApplications();
    }

    @Override
    public void removeApplication(String id) {
        database.removeApplication(id);
    }

    @Override
    public void saveVote(Vote vote) {
        database.saveVote(vote);
    }

    @Override
    public void removeVote(String applicationId, String expertId) {
        database.removeVote(applicationId, expertId);
    }

    @Override
    public List<Vote> getVotes(String applicationId) {
        return database.getVotes(applicationId);
    }

    @Override
    public CompetitionResult getResult() {
        return database.getResult();
    }

    @Override
    public void saveResult(CompetitionResult result) {
        database.saveResult(result);
    }

    @Override
    public void clear() {
        database.clear();
    }
}
