package user;

public class UserToken {
    String token;

    public UserToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
