package academy.tochkavhoda.competition.server;

import academy.tochkavhoda.competition.dto.response.ServerResponse;
import academy.tochkavhoda.competition.service.CompetitionService;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

public class Server {
    private final CompetitionService service;

    public Server(BigDecimal fund, BigDecimal threshold, Instant deadline) {
        this(fund, threshold, deadline, Clock.systemUTC());
    }

    public Server(BigDecimal fund, BigDecimal threshold, Instant deadline, Clock clock) {
        service = new CompetitionService(fund, threshold, deadline, clock);
    }

    public ServerResponse registerParticipant(String requestJson) {
        return service.registerParticipant(requestJson);
    }

    public ServerResponse registerExpert(String requestJson) {
        return service.registerExpert(requestJson);
    }

    public ServerResponse login(String requestJson) {
        return service.login(requestJson);
    }

    public ServerResponse logout(String token) {
        return service.logout(token);
    }

    public ServerResponse deleteAccount(String token) {
        return service.deleteAccount(token);
    }

    public ServerResponse addApplication(String token, String requestJson) {
        return service.addApplication(token, requestJson);
    }

    public ServerResponse addApplications(String token, String requestJson) {
        return service.addApplications(token, requestJson);
    }

    public ServerResponse cancelApplication(String token, String requestJson) {
        return service.cancelApplication(token, requestJson);
    }

    public ServerResponse getMyApplications(String token) {
        return service.getMyApplications(token);
    }

    public ServerResponse getApplications(String token, String requestJson) {
        return service.getApplications(token, requestJson);
    }

    public ServerResponse getRatedApplications(String token) {
        return service.getRatedApplications(token);
    }

    public ServerResponse setVote(String token, String requestJson) {
        return service.setVote(token, requestJson);
    }

    public ServerResponse deleteVote(String token, String requestJson) {
        return service.deleteVote(token, requestJson);
    }

    public ServerResponse getResults(String token) {
        return service.getResults(token);
    }

    public void clear() {
        service.clear();
    }
}
