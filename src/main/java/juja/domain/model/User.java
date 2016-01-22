package juja.domain.model;

public class User {
    public String slackNickName;

    public static UserBuilder create() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private User user = new User();

        public UserBuilder withSlackNick(String slackNick) {
            user.slackNickName = slackNick;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
