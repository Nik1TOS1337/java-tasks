package academy.tochkavhoda.competition.model;

import java.util.Set;

public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String company;
    private final Set<String> directions;
    private final String login;
    private final String passwordHash;
    private final String salt;
    private final Role role;

    public User(String id, String firstName, String lastName, String company, Set<String> directions, String login, String passwordHash, String salt, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.directions = Set.copyOf(directions);
        this.login = login;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public Set<String> getDirections() {
        return directions;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public Role getRole() {
        return role;
    }
}
