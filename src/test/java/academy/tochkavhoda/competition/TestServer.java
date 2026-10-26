package academy.tochkavhoda.competition;

import academy.tochkavhoda.competition.server.Server;
import academy.tochkavhoda.competition.dto.request.*;
import academy.tochkavhoda.competition.dto.response.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestServer {
    private static final String PASSWORD = "secret123";
    private static final Instant DEADLINE = Instant.parse("2030-01-01T00:00:00Z");
    private final Gson gson = new Gson();
    private Server server;
    private TestClock clock;

    @BeforeEach
    public void prepare() {
        clock = new TestClock(DEADLINE.minusSeconds(60));
        server = new Server(new BigDecimal("1000.00"), new BigDecimal("3"), DEADLINE, clock);
        server.clear();
    }

    private String json(Object value) {
        return gson.toJson(value);
    }

    private <T> T ok(ServerResponse response, Class<T> type) {
        assertEquals(200, response.getResponseCode(), response.getResponseData());
        return gson.fromJson(response.getResponseData(), type);
    }

    private void ok(ServerResponse response) {
        assertEquals(200, response.getResponseCode(), response.getResponseData());
        assertEquals("{}", response.getResponseData());
    }

    private void error(ServerResponse response, String expected) {
        assertEquals(400, response.getResponseCode(), response.getResponseData());
        ErrorResponse value = gson.fromJson(response.getResponseData(), ErrorResponse.class);
        assertNotNull(value.getError());
        assertTrue(value.getError().contains(expected), value.getError());
    }

    private RegisterParticipantRequest participantRequest(String login, String company) {
        return new RegisterParticipantRequest("Иван", "Иванов", company, login, PASSWORD);
    }

    private String signIn(String login) {
        return ok(server.login(json(new LoginRequest(login, PASSWORD))), TokenResponse.class).getToken();
    }

    private String participant(String login) {
        ok(server.registerParticipant(json(participantRequest(login, "Фирма " + login))));
        return signIn(login);
    }

    private String expert(String login, String... directions) {
        ok(server.registerExpert(json(new RegisterExpertRequest("Пётр", login,
                Arrays.asList(directions), login, PASSWORD))));
        return signIn(login);
    }

    private ApplicationRequest applicationRequest(String title, String amount, String... directions) {
        return new ApplicationRequest(title, "Описание проекта", Arrays.asList(directions), new BigDecimal(amount));
    }

    private String add(String token, String title, String amount, String... directions) {
        return ok(server.addApplication(token, json(applicationRequest(title, amount, directions))),
                IdsResponse.class).getApplicationIds().get(0);
    }

    private void vote(String token, String id, int score) {
        ok(server.setVote(token, json(new VoteRequest(id, score))));
    }

    private List<ApplicationResponse> mine(String token) {
        return ok(server.getMyApplications(token), ApplicationsResponse.class).getApplications();
    }

    private List<ApplicationResponse> search(String token, String... directions) {
        return ok(server.getApplications(token, json(new DirectionsRequest(Arrays.asList(directions)))),
                ApplicationsResponse.class).getApplications();
    }

    private ResultsResponse results(String token) {
        return ok(server.getResults(token), ResultsResponse.class);
    }

    @Test
    public void testRegistrationAndLogin() {
        String token = participant("alice");
        assertFalse(token.isBlank());
        assertTrue(mine(token).isEmpty());
        error(server.login(json(new LoginRequest("alice", "wrong12"))), "Неверный логин или пароль");
        error(server.login(json(new LoginRequest("unknown", PASSWORD))), "Неверный логин или пароль");
        error(server.registerParticipant(json(participantRequest("alice", "Другая фирма"))), "Логин уже занят");
        error(server.registerParticipant(json(participantRequest("other", "  фирма   ALICE  "))), "Фирма уже зарегистрирована");
        error(server.registerExpert(json(new RegisterExpertRequest("Анна", "Петрова", List.of("математика"), "alice", PASSWORD))), "Логин уже занят");
        expert("expert", "математика");
        error(server.registerExpert(json(new RegisterExpertRequest("пётр", "EXPERT", List.of("физика"), "newlogin", PASSWORD))), "Эксперт уже зарегистрирован");
    }

    @Test
    public void testInvalidRegistration() {
        RegisterParticipantRequest request = participantRequest("user", "Фирма");
        request.setFirstName(null);
        error(server.registerParticipant(json(request)), "firstName");
        request.setFirstName(" ");
        error(server.registerParticipant(json(request)), "firstName");
        request.setFirstName("Иван");
        request.setCompany("");
        error(server.registerParticipant(json(request)), "company");
        request.setCompany("Фирма");
        request.setLogin("ab");
        error(server.registerParticipant(json(request)), "login");
        request.setLogin("a b");
        error(server.registerParticipant(json(request)), "недопустимые");
        request.setLogin("user");
        request.setPassword("12345");
        error(server.registerParticipant(json(request)), "Пароль");
        error(server.registerExpert(json(new RegisterExpertRequest("Пётр", "Петров", List.of(), "expert", PASSWORD))), "направления");
        error(server.registerExpert(json(new RegisterExpertRequest("Пётр", "Петров", Arrays.asList((String) null), "expert", PASSWORD))), "directions");
    }

    @Test
    public void testInvalidJson() {
        for (String value : Arrays.asList(null, "", " ", "null", "[]", "{", "{unquoted: 1}", "{} {}", "true", "42")) {
            ServerResponse response = server.registerParticipant(value);
            assertEquals(400, response.getResponseCode(), String.valueOf(value));
            assertNotNull(gson.fromJson(response.getResponseData(), ErrorResponse.class).getError());
        }
        String participant = participant("alice");
        String expert = expert("expert", "математика");
        error(server.addApplication(participant, "{}"), "title");
        error(server.addApplications(participant, "{}"), "заявки");
        error(server.setVote(expert, "{}"), "applicationId");
        error(server.cancelApplication(participant, "{}"), "applicationId");
        error(server.getApplications(expert, "{}"), "направления");
    }

    @Test
    public void testSessions() {
        String first = participant("alice");
        String second = signIn("ALICE");
        assertNotEquals(first, second);
        error(server.getMyApplications(first), "Токен");
        ok(server.logout(second));
        error(server.logout(second), "Токен");
        error(server.getMyApplications(second), "Токен");
        String third = signIn("alice");
        assertNotEquals(second, third);
        assertTrue(mine(third).isEmpty());
        error(server.getMyApplications(null), "Токен");
        error(server.deleteAccount("bad"), "Токен");
    }

    @Test
    public void testSingleAndBatchApplications() {
        String token = participant("alice");
        String first = add(token, "Первый", "100.50", "математика");
        ApplicationsRequest request = new ApplicationsRequest(List.of(
                applicationRequest("Второй", "200", "информатика"),
                applicationRequest("Третий", "300", "математика", "информатика")));
        List<String> ids = ok(server.addApplications(token, json(request)), IdsResponse.class).getApplicationIds();
        assertEquals(2, ids.size());
        assertEquals(3, mine(token).size());
        assertEquals(3, new HashSet<>(List.of(first, ids.get(0), ids.get(1))).size());
        assertEquals(0, mine(token).get(0).getAmount().compareTo(new BigDecimal("100.50")));
        ok(server.cancelApplication(token, json(new IdRequest(first))));
        assertEquals(2, mine(token).size());
        error(server.cancelApplication(token, json(new IdRequest(first))), "не найдена");
    }

    @Test
    public void testAtomicBatchAndInvalidAmounts() {
        String token = participant("alice");
        ApplicationsRequest request = new ApplicationsRequest(Arrays.asList(
                applicationRequest("Первый", "100", "математика"),
                applicationRequest("Второй", "-1", "математика")));
        error(server.addApplications(token, json(request)), "Сумма");
        assertTrue(mine(token).isEmpty());
        request.setApplications(Arrays.asList(applicationRequest("Первый", "100", "математика"), null));
        error(server.addApplications(token, json(request)), "Не задана заявка");
        assertTrue(mine(token).isEmpty());
        for (String amount : List.of("0", "-1", "0.001")) {
            error(server.addApplication(token, json(applicationRequest("Проект", amount, "математика"))), "Сумма");
        }
        ApplicationRequest invalid = applicationRequest("Проект", "100", "математика");
        invalid.setAmount(null);
        error(server.addApplication(token, json(invalid)), "Сумма");
        invalid.setAmount(BigDecimal.ONE);
        invalid.setDirections(List.of());
        error(server.addApplication(token, json(invalid)), "направления");
        invalid.setDirections(List.of("математика"));
        invalid.setDescription(" ");
        error(server.addApplication(token, json(invalid)), "description");
        assertTrue(mine(token).isEmpty());
    }

    @Test
    public void testOwnershipAndRoles() {
        String owner = participant("owner");
        String other = participant("other");
        String expert = expert("expert", "математика");
        String id = add(owner, "Проект", "100", "математика");
        error(server.cancelApplication(other, json(new IdRequest(id))), "чужую");
        error(server.addApplication(expert, json(applicationRequest("Проект", "100", "математика"))), "роли");
        error(server.setVote(owner, json(new VoteRequest(id, 5))), "роли");
        error(server.getMyApplications(expert), "роли");
        error(server.getApplications(owner, json(new DirectionsRequest(List.of("математика")))), "роли");
        error(server.getRatedApplications(owner), "роли");
        assertTrue(mine(other).isEmpty());
        assertEquals(1, mine(owner).size());
    }

    @Test
    public void testDirectionSearch() {
        String participant = participant("alice");
        String expert = expert("expert", " МАТЕМАТИКА ", "информатика");
        add(participant, "Первый", "100", "Математика");
        add(participant, "Второй", "100", "информатика", "математика");
        add(participant, "Третий", "100", "физика");
        assertEquals(2, search(expert, "математика").size());
        assertEquals(1, search(expert, "информатика").size());
        assertEquals(2, search(expert, "математика", "информатика").size());
        error(server.getApplications(expert, json(new DirectionsRequest(List.of("физика")))), "недоступные");
        error(server.getApplications(expert, json(new DirectionsRequest(List.of()))), "направления");
    }

    @Test
    public void testVotesCanBeChangedAndDeleted() {
        String participant = participant("alice");
        String first = expert("first", "математика");
        String second = expert("second", "математика");
        String id = add(participant, "Проект", "100", "математика", "физика");
        vote(first, id, 5);
        vote(second, id, 3);
        ApplicationResponse value = search(first, "математика").get(0);
        assertEquals(2, value.getVoteCount());
        assertEquals(0, value.getAverageScore().compareTo(new BigDecimal("4")));
        assertEquals(5, value.getMyScore());
        vote(first, id, 1);
        value = search(first, "математика").get(0);
        assertEquals(2, value.getVoteCount());
        assertEquals(0, value.getAverageScore().compareTo(new BigDecimal("2")));
        assertEquals(1, ok(server.getRatedApplications(first), ApplicationsResponse.class).getApplications().size());
        ok(server.deleteVote(first, json(new IdRequest(id))));
        assertTrue(ok(server.getRatedApplications(first), ApplicationsResponse.class).getApplications().isEmpty());
        assertEquals(1, search(second, "математика").get(0).getVoteCount());
        error(server.deleteVote(first, json(new IdRequest(id))), "не найдена");
    }

    @Test
    public void testInvalidVotes() {
        String participant = participant("alice");
        String expert = expert("expert", "математика");
        String allowed = add(participant, "Первый", "100", "математика");
        String forbidden = add(participant, "Второй", "100", "физика");
        for (Integer score : Arrays.asList(null, 0, 6, -1)) {
            error(server.setVote(expert, json(new VoteRequest(allowed, score))), "от 1 до 5");
        }
        error(server.setVote(expert, json(new VoteRequest(forbidden, 5))), "недоступны");
        error(server.setVote(expert, json(new VoteRequest("missing", 5))), "не найдена");
        error(server.setVote(expert, "{\"applicationId\":\"" + allowed + "\",\"score\":2.5}"), "JSON");
        assertEquals(0, mine(participant).get(0).getVoteCount());
    }

    @Test
    public void testAccountAndApplicationDeletion() {
        String participant = participant("alice");
        String first = expert("first", "математика");
        String second = expert("second", "математика");
        String id = add(participant, "Проект", "100", "математика");
        vote(first, id, 5);
        vote(second, id, 1);
        ok(server.deleteAccount(second));
        error(server.getRatedApplications(second), "Токен");
        assertEquals(1, mine(participant).get(0).getVoteCount());
        assertEquals(0, mine(participant).get(0).getAverageScore().compareTo(new BigDecimal("5")));
        ok(server.cancelApplication(participant, json(new IdRequest(id))));
        assertTrue(ok(server.getRatedApplications(first), ApplicationsResponse.class).getApplications().isEmpty());
        id = add(participant, "Новый", "100", "математика");
        vote(first, id, 5);
        ok(server.deleteAccount(participant));
        assertTrue(search(first, "математика").isEmpty());
        error(server.login(json(new LoginRequest("alice", PASSWORD))), "Неверный логин");
        error(server.getMyApplications(participant), "Токен");
        String newToken = participant("alice");
        assertTrue(mine(newToken).isEmpty());
    }

    @Test
    public void testRankingBudgetAndThreshold() {
        String participant = participant("alice");
        String expert = expert("expert", "математика");
        String costly = add(participant, "Дорогой", "1200", "математика");
        String large = add(participant, "Большой", "700", "математика");
        String small = add(participant, "Малый", "200", "математика");
        String medium = add(participant, "Средний", "400", "математика");
        String threshold = add(participant, "Порог", "50", "математика");
        String low = add(participant, "Низкий", "10", "математика");
        add(participant, "Без оценок", "1", "математика");
        vote(expert, costly, 5);
        vote(expert, large, 4);
        vote(expert, small, 4);
        vote(expert, medium, 4);
        vote(expert, threshold, 3);
        vote(expert, low, 2);
        error(server.getResults(participant), "ещё не подведены");
        clock.set(DEADLINE);
        ResultsResponse result = results(participant);
        List<GrantResponse> values = result.getApplications();
        assertEquals(7, values.size());
        assertEquals(costly, values.get(0).getApplication().getId());
        assertFalse(values.get(0).getFunded());
        assertEquals(small, values.get(1).getApplication().getId());
        assertTrue(values.get(1).getFunded());
        assertEquals(medium, values.get(2).getApplication().getId());
        assertTrue(values.get(2).getFunded());
        assertEquals(large, values.get(3).getApplication().getId());
        for (int index = 3; index < values.size(); index++) {
            assertFalse(values.get(index).getFunded());
        }
        assertEquals(0, result.getRemainingFund().compareTo(new BigDecimal("400")));
    }

    @Test
    public void testSkipExpensiveApplicationAndExactBudget() {
        String participant = participant("alice");
        String expert = expert("expert", "математика");
        String costly = add(participant, "Первый", "1000.01", "математика");
        String affordable = add(participant, "Второй", "1000", "математика");
        vote(expert, costly, 5);
        vote(expert, affordable, 4);
        clock.set(DEADLINE);
        ResultsResponse result = results(expert);
        assertFalse(result.getApplications().get(0).getFunded());
        assertTrue(result.getApplications().get(1).getFunded());
        assertEquals(0, result.getRemainingFund().signum());
    }

    @Test
    public void testDeadlineAndFrozenResults() {
        String participant = participant("alice");
        String expert = expert("expert", "математика");
        String id = add(participant, "Проект", "100", "математика");
        vote(expert, id, 5);
        clock.set(DEADLINE.minusNanos(1));
        vote(expert, id, 4);
        clock.set(DEADLINE);
        error(server.setVote(expert, json(new VoteRequest(id, 1))), "завершён");
        error(server.deleteVote(expert, json(new IdRequest(id))), "завершён");
        error(server.cancelApplication(participant, json(new IdRequest(id))), "завершён");
        error(server.addApplication(participant, json(applicationRequest("Поздно", "1", "математика"))), "завершён");
        error(server.addApplications(participant, json(new ApplicationsRequest(List.of(applicationRequest("Поздно", "1", "математика"))))), "завершён");
        String frozen = server.getResults(participant).getResponseData();
        assertEquals(0, results(expert).getApplications().get(0).getApplication().getAverageScore().compareTo(new BigDecimal("4")));
        ok(server.deleteAccount(expert));
        assertEquals(frozen, server.getResults(participant).getResponseData());
        ok(server.deleteAccount(participant));
        String observer = expert("observer", "математика");
        assertEquals(frozen, server.getResults(observer).getResponseData());
        assertTrue(search(observer, "математика").isEmpty());
    }

    @Test
    public void testTiesRemainStable() {
        String participant = participant("alice");
        String expert = expert("expert", "математика");
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            String id = add(participant, "Проект " + i, "300", "математика");
            ids.add(id);
            vote(expert, id, 5);
        }
        clock.set(DEADLINE);
        String first = server.getResults(participant).getResponseData();
        assertEquals(first, server.getResults(expert).getResponseData());
        ResultsResponse result = results(participant);
        assertEquals(3, result.getApplications().stream().filter(GrantResponse::getFunded).count());
        Set<String> actual = new HashSet<>();
        result.getApplications().forEach(value -> actual.add(value.getApplication().getId()));
        assertEquals(ids, actual);
        assertEquals(0, result.getRemainingFund().compareTo(new BigDecimal("100")));
    }

    @Test
    public void testEmptyCompetitionAndNoScores() {
        String participant = participant("alice");
        add(participant, "Без оценок", "100", "математика");
        clock.set(DEADLINE);
        ResultsResponse result = results(participant);
        assertFalse(result.getApplications().get(0).getFunded());
        assertEquals(0, result.getRemainingFund().compareTo(new BigDecimal("1000")));
        server.clear();
        participant = participant("alice");
        result = results(participant);
        assertTrue(result.getApplications().isEmpty());
        assertEquals(0, result.getRemainingFund().compareTo(new BigDecimal("1000")));
    }

    @Test
    public void testSharedServerState() {
        String participant = participant("alice");
        add(participant, "Проект", "100", "математика");
        Server another = new Server(new BigDecimal("1000"), new BigDecimal("3"), DEADLINE, clock);
        assertEquals(1, ok(another.getMyApplications(participant), ApplicationsResponse.class).getApplications().size());
        ok(another.logout(participant));
        error(server.getMyApplications(participant), "Токен");
        assertThrows(IllegalArgumentException.class,
                () -> new Server(BigDecimal.ONE, BigDecimal.ZERO, DEADLINE, clock));
    }

    @Test
    public void testInvalidSettings() {
        assertThrows(IllegalArgumentException.class, () -> new Server(null, BigDecimal.ONE, DEADLINE));
        assertThrows(IllegalArgumentException.class, () -> new Server(new BigDecimal("-1"), BigDecimal.ONE, DEADLINE));
        assertThrows(IllegalArgumentException.class, () -> new Server(new BigDecimal("0.001"), BigDecimal.ONE, DEADLINE));
        assertThrows(IllegalArgumentException.class, () -> new Server(BigDecimal.ONE, new BigDecimal("6"), DEADLINE));
        assertThrows(IllegalArgumentException.class, () -> new Server(BigDecimal.ONE, new BigDecimal("-1"), DEADLINE));
        assertThrows(IllegalArgumentException.class, () -> new Server(BigDecimal.ONE, BigDecimal.ONE, null));
    }

    private static class TestClock extends Clock {
        private Instant now;
        private final ZoneId zone;

        private TestClock(Instant now) {
            this(now, ZoneOffset.UTC);
        }

        private TestClock(Instant now, ZoneId zone) {
            this.now = now;
            this.zone = zone;
        }

        public void set(Instant now) {
            this.now = now;
        }

        @Override
        public ZoneId getZone() {
            return zone;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return new TestClock(now, zone);
        }

        @Override
        public Instant instant() {
            return now;
        }
    }
}
