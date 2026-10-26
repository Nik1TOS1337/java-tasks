package academy.tochkavhoda.competition.service;

import academy.tochkavhoda.competition.dao.CompetitionDao;
import academy.tochkavhoda.competition.daoimpl.CompetitionDaoImpl;
import academy.tochkavhoda.competition.dto.request.*;
import academy.tochkavhoda.competition.dto.response.*;
import academy.tochkavhoda.competition.exception.CompetitionException;
import academy.tochkavhoda.competition.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.Strictness;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;

public class CompetitionService {
    private static final Object LOCK = new Object();
    private final CompetitionDao dao = new CompetitionDaoImpl();
    private final Gson gson = new GsonBuilder().setStrictness(Strictness.STRICT).create();
    private final Clock clock;

    public CompetitionService(BigDecimal fund, BigDecimal threshold, Instant deadline, Clock clock) {
        if (fund == null || fund.signum() < 0 || fund.stripTrailingZeros().scale() > 2
                || threshold == null || threshold.signum() < 0
                || threshold.compareTo(BigDecimal.valueOf(5)) > 0
                || deadline == null || clock == null) {
            throw new IllegalArgumentException("Неверные параметры конкурса");
        }
        this.clock = clock;
        synchronized (LOCK) {
            dao.configure(new Settings(fund, threshold, deadline));
        }
    }

    private ServerResponse execute(Supplier<Object> action) {
        synchronized (LOCK) {
            try {
                finishIfDue();
                return new ServerResponse(200, gson.toJson(action.get()));
            } catch (CompetitionException e) {
                return new ServerResponse(400, gson.toJson(new ErrorResponse(e.getMessage())));
            }
        }
    }

    private <T> T read(String json, Class<T> type) {
        if (json == null || json.isBlank()) {
            throw new CompetitionException("Запрос не задан");
        }
        try {
            T value = gson.fromJson(json, type);
            if (value == null) {
                throw new CompetitionException("Запрос не задан");
            }
            return value;
        } catch (JsonParseException | IllegalStateException e) {
            throw new CompetitionException("Неверный формат JSON");
        }
    }

    private String text(String value, String field, int min, int max) {
        if (value == null) {
            throw new CompetitionException("Не задано поле " + field);
        }
        String result = value.trim().replaceAll("\\s+", " ");
        if (result.length() < min || result.length() > max) {
            throw new CompetitionException("Неверная длина поля " + field);
        }
        return result;
    }

    private String validateLogin(String value) {
        String result = text(value, "login", 3, 64).toLowerCase(Locale.ROOT);
        if (!result.matches("[a-z0-9_.@]+")) {
            throw new CompetitionException("Логин содержит недопустимые знаки");
        }
        return result;
    }

    private String password(String value) {
        if (value == null || value.isBlank() || value.length() < 6 || value.length() > 128) {
            throw new CompetitionException("Пароль должен содержать от 6 до 128 знаков");
        }
        return value;
    }

    private Set<String> directions(List<String> values) {
        if (values == null || values.isEmpty()) {
            throw new CompetitionException("Не заданы направления");
        }
        Set<String> result = new LinkedHashSet<>();
        for (String value : values) {
            result.add(text(value, "directions", 1, 100).toLowerCase(Locale.ROOT));
        }
        return result;
    }

    private User user(String token) {
        User user = token == null ? null : dao.findByToken(token);
        if (user == null) {
            throw new CompetitionException("Токен недействителен");
        }
        return user;
    }

    private User user(String token, Role role) {
        User user = user(token);
        if (user.getRole() != role) {
            throw new CompetitionException("Операция недоступна для этой роли");
        }
        return user;
    }

    private void requireOpen() {
        if (!clock.instant().isBefore(dao.getSettings().getDeadline()) || dao.getResult() != null) {
            throw new CompetitionException("Конкурс завершён");
        }
    }

    private Application application(String id) {
        String applicationId = text(id, "applicationId", 1, 100);
        Application application = dao.findApplication(applicationId);
        if (application == null) {
            throw new CompetitionException("Заявка не найдена");
        }
        return application;
    }

    private void checkDirections(User expert, Application application) {
        if (Collections.disjoint(expert.getDirections(), application.getDirections())) {
            throw new CompetitionException("Направления заявки недоступны эксперту");
        }
    }

    public ServerResponse registerParticipant(String json) {
        return execute(() -> {
            RegisterParticipantRequest request = read(json, RegisterParticipantRequest.class);
            String firstName = text(request.getFirstName(), "firstName", 1, 100);
            String lastName = text(request.getLastName(), "lastName", 1, 100);
            String company = text(request.getCompany(), "company", 1, 200);
            String login = validateLogin(request.getLogin());
            String password = password(request.getPassword());
            String salt = Passwords.salt();
            dao.insertUser(new User(UUID.randomUUID().toString(), firstName, lastName, company,
                    Collections.emptySet(), login, Passwords.hash(password, salt), salt, Role.PARTICIPANT));
            return Collections.emptyMap();
        });
    }

    public ServerResponse registerExpert(String json) {
        return execute(() -> {
            RegisterExpertRequest request = read(json, RegisterExpertRequest.class);
            String firstName = text(request.getFirstName(), "firstName", 1, 100);
            String lastName = text(request.getLastName(), "lastName", 1, 100);
            Set<String> directions = directions(request.getDirections());
            String login = validateLogin(request.getLogin());
            String password = password(request.getPassword());
            String salt = Passwords.salt();
            dao.insertUser(new User(UUID.randomUUID().toString(), firstName, lastName, null,
                    directions, login, Passwords.hash(password, salt), salt, Role.EXPERT));
            return Collections.emptyMap();
        });
    }

    public ServerResponse login(String json) {
        return execute(() -> {
            LoginRequest request = read(json, LoginRequest.class);
            User user = dao.findByLogin(validateLogin(request.getLogin()));
            String password = password(request.getPassword());
            if (user == null || !Passwords.matches(password, user.getSalt(), user.getPasswordHash())) {
                throw new CompetitionException("Неверный логин или пароль");
            }
            String token = UUID.randomUUID().toString();
            dao.addSession(token, user.getId());
            return new TokenResponse(token);
        });
    }

    public ServerResponse logout(String token) {
        return execute(() -> {
            user(token);
            dao.removeSession(token);
            return Collections.emptyMap();
        });
    }

    public ServerResponse deleteAccount(String token) {
        return execute(() -> {
            User user = user(token);
            dao.removeUser(user.getId());
            return Collections.emptyMap();
        });
    }

    private Application createApplication(User participant, ApplicationRequest request) {
        if (request == null) {
            throw new CompetitionException("Не задана заявка");
        }
        String title = text(request.getTitle(), "title", 1, 200);
        String description = text(request.getDescription(), "description", 1, 10000);
        Set<String> directions = directions(request.getDirections());
        BigDecimal amount = request.getAmount();
        if (amount == null || amount.signum() <= 0 || amount.stripTrailingZeros().scale() > 2) {
            throw new CompetitionException("Сумма должна быть положительной и содержать не больше двух знаков после запятой");
        }
        return new Application(UUID.randomUUID().toString(), participant.getId(), title,
                description, directions, amount);
    }

    public ServerResponse addApplication(String token, String json) {
        return execute(() -> {
            User participant = user(token, Role.PARTICIPANT);
            requireOpen();
            Application value = createApplication(participant, read(json, ApplicationRequest.class));
            dao.insertApplications(Collections.singletonList(value));
            return new IdsResponse(Collections.singletonList(value.getId()));
        });
    }

    public ServerResponse addApplications(String token, String json) {
        return execute(() -> {
            User participant = user(token, Role.PARTICIPANT);
            requireOpen();
            ApplicationsRequest request = read(json, ApplicationsRequest.class);
            if (request.getApplications() == null || request.getApplications().isEmpty()) {
                throw new CompetitionException("Не заданы заявки");
            }
            List<Application> values = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            for (ApplicationRequest item : request.getApplications()) {
                Application value = createApplication(participant, item);
                values.add(value);
                ids.add(value.getId());
            }
            dao.insertApplications(values);
            return new IdsResponse(ids);
        });
    }

    public ServerResponse cancelApplication(String token, String json) {
        return execute(() -> {
            User participant = user(token, Role.PARTICIPANT);
            requireOpen();
            Application application = application(read(json, IdRequest.class).getApplicationId());
            if (!application.getParticipantId().equals(participant.getId())) {
                throw new CompetitionException("Нельзя отменить чужую заявку");
            }
            dao.removeApplication(application.getId());
            return Collections.emptyMap();
        });
    }

    public ServerResponse getMyApplications(String token) {
        return execute(() -> {
            User participant = user(token, Role.PARTICIPANT);
            List<ApplicationResponse> values = new ArrayList<>();
            for (Application application : dao.getApplications()) {
                if (application.getParticipantId().equals(participant.getId())) {
                    values.add(view(application, null));
                }
            }
            return new ApplicationsResponse(values);
        });
    }

    public ServerResponse getApplications(String token, String json) {
        return execute(() -> {
            User expert = user(token, Role.EXPERT);
            Set<String> requested = directions(read(json, DirectionsRequest.class).getDirections());
            if (!expert.getDirections().containsAll(requested)) {
                throw new CompetitionException("Запрошены недоступные направления");
            }
            List<ApplicationResponse> values = new ArrayList<>();
            for (Application application : dao.getApplications()) {
                if (!Collections.disjoint(requested, application.getDirections())) {
                    values.add(view(application, expert.getId()));
                }
            }
            return new ApplicationsResponse(values);
        });
    }

    public ServerResponse getRatedApplications(String token) {
        return execute(() -> {
            User expert = user(token, Role.EXPERT);
            List<ApplicationResponse> values = new ArrayList<>();
            for (Application application : dao.getApplications()) {
                ApplicationResponse value = view(application, expert.getId());
                if (value.getMyScore() != null) {
                    values.add(value);
                }
            }
            return new ApplicationsResponse(values);
        });
    }

    public ServerResponse setVote(String token, String json) {
        return execute(() -> {
            User expert = user(token, Role.EXPERT);
            requireOpen();
            VoteRequest request = read(json, VoteRequest.class);
            Application application = application(request.getApplicationId());
            checkDirections(expert, application);
            if (request.getScore() == null || request.getScore() < 1 || request.getScore() > 5) {
                throw new CompetitionException("Оценка должна быть от 1 до 5");
            }
            dao.saveVote(new Vote(expert.getId(), application.getId(), request.getScore()));
            return Collections.emptyMap();
        });
    }

    public ServerResponse deleteVote(String token, String json) {
        return execute(() -> {
            User expert = user(token, Role.EXPERT);
            requireOpen();
            Application application = application(read(json, IdRequest.class).getApplicationId());
            checkDirections(expert, application);
            dao.removeVote(application.getId(), expert.getId());
            return Collections.emptyMap();
        });
    }

    private ApplicationResponse view(Application application, String expertId) {
        long sum = 0;
        Integer own = null;
        List<Vote> votes = dao.getVotes(application.getId());
        for (Vote vote : votes) {
            sum += vote.getScore();
            if (vote.getExpertId().equals(expertId)) {
                own = vote.getScore();
            }
        }
        return view(application, sum, votes.size(), own);
    }

    private ApplicationResponse view(Application application, long sum, int count, Integer own) {
        BigDecimal average = count == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(sum)
                .divide(BigDecimal.valueOf(count), 6, RoundingMode.HALF_UP);
        List<String> directions = new ArrayList<>(application.getDirections());
        Collections.sort(directions);
        return new ApplicationResponse(application.getId(), application.getParticipantId(),
                application.getTitle(), application.getDescription(), directions,
                application.getAmount(), average, count, own);
    }

    private void finishIfDue() {
        Settings settings = dao.getSettings();
        if (clock.instant().isBefore(settings.getDeadline()) || dao.getResult() != null) {
            return;
        }
        List<GrantResult> ranked = new ArrayList<>();
        for (Application application : dao.getApplications()) {
            List<Vote> votes = dao.getVotes(application.getId());
            long sum = 0;
            for (Vote vote : votes) {
                sum += vote.getScore();
            }
            ranked.add(new GrantResult(application, sum, votes.size(), false));
        }
        Collections.shuffle(ranked);
        ranked.sort((first, second) -> {
            BigDecimal firstValue = BigDecimal.valueOf(first.getScoreSum())
                    .multiply(BigDecimal.valueOf(Math.max(1, second.getVoteCount())));
            BigDecimal secondValue = BigDecimal.valueOf(second.getScoreSum())
                    .multiply(BigDecimal.valueOf(Math.max(1, first.getVoteCount())));
            int comparison = secondValue.compareTo(firstValue);
            return comparison == 0 ? first.getApplication().getAmount()
                    .compareTo(second.getApplication().getAmount()) : comparison;
        });
        BigDecimal remaining = settings.getFund();
        List<GrantResult> result = new ArrayList<>();
        for (GrantResult item : ranked) {
            boolean aboveThreshold = item.getVoteCount() > 0
                    && BigDecimal.valueOf(item.getScoreSum()).compareTo(settings.getThreshold()
                    .multiply(BigDecimal.valueOf(item.getVoteCount()))) > 0;
            boolean funded = aboveThreshold && item.getApplication().getAmount().compareTo(remaining) <= 0;
            if (funded) {
                remaining = remaining.subtract(item.getApplication().getAmount());
            }
            result.add(new GrantResult(item.getApplication(), item.getScoreSum(), item.getVoteCount(), funded));
        }
        dao.saveResult(new CompetitionResult(result, remaining));
    }

    public ServerResponse getResults(String token) {
        return execute(() -> {
            user(token);
            CompetitionResult result = dao.getResult();
            if (result == null) {
                throw new CompetitionException("Итоги ещё не подведены");
            }
            List<GrantResponse> values = new ArrayList<>();
            for (GrantResult item : result.getApplications()) {
                values.add(new GrantResponse(view(item.getApplication(), item.getScoreSum(),
                        item.getVoteCount(), null), item.getFunded()));
            }
            return new ResultsResponse(values, result.getRemainingFund());
        });
    }

    public void clear() {
        synchronized (LOCK) {
            dao.clear();
        }
    }
}
