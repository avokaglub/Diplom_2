package user;
import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandomUser() {
        return new User(
                RandomStringUtils.randomAlphabetic(5, 10).toLowerCase() + "@example.com",
                "qwerty",
                "Maria"
        );
    }

    public static User getUserWithNullEmail() {
        return new User(
                null,
                "12345",
                "Maria"
        );
    }

    public static User getUserWithNullPassword() {
        return new User(
                "avokaglub777@example.com",
                null,
                RandomStringUtils.randomAlphabetic(7).toLowerCase()
        );
    }

    public static User getUserWitchExist() {
        return new User(
                "avokaglub@example.com",
                "qwerty",
                "Maria"
        );
    }

    public static User getUserToSuccessLogin() {
        return new User(
                "avokaglub@example.com",
                "qwerty",
                "Maria"
        );
    }

    public static User getUserWithEmptyLogin() {
        return new User(
                "",
                "qwerty",
                "Maria"
        );
    }

    public static User getUserWithEmptyPassword() {
        return new User(
                "avokaglub@example.com",
                "",
                "Maria"
        );
    }

    public static User getUserWithInvalidLogin() {
        return new User(
                "avokaglub777@example.ru",
                "12345",
                "Maria"
        );
    }

    public static User getUserWithInvalidPassword() {
        return new User(
                "avokaglub@example.com",
                "qwertyui",
                "Maria"
        );
    }

    @Override
    public String toString() {
        return "[User] Email: " + this.email + "; Password: " + this.password + "; Name: " + this.name + ";";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
