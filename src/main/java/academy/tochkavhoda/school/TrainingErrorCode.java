package academy.tochkavhoda.school;

public enum TrainingErrorCode {
    TRAINEE_WRONG_FIRSTNAME("Wrong trainee first name"),
    TRAINEE_WRONG_LASTNAME("Wrong trainee last name"),
    TRAINEE_WRONG_RATING("Wrong trainee rating"),
    GROUP_WRONG_NAME("Wrong group name"),
    GROUP_WRONG_ROOM("Wrong group room"),
    TRAINEE_NOT_FOUND("Trainee not found"),
    SCHOOL_WRONG_NAME("Wrong school name"),
    DUPLICATE_GROUP_NAME("Duplicate group name"),
    GROUP_NOT_FOUND("Group not found"),
    DUPLICATE_TRAINEE("Duplicate trainee"),
    EMPTY_TRAINEE_QUEUE("Trainee queue is empty"),
    DUPLICATE_PASSPORT("Duplicate passport"),
    PASSPORT_NOT_FOUND("Passport not found");

    private final String errorString;

    TrainingErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
