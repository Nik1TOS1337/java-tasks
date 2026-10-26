package academy.tochkavhoda.competition.dto.request;


public class RegisterParticipantRequest {
    private String firstName;
    private String lastName;
    private String company;
    private String login;
    private String password;

    public RegisterParticipantRequest() {
    }

    public RegisterParticipantRequest(String firstName, String lastName, String company, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
