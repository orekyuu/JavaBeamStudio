package net.orekyuu.javatter.api.twitter.control;

public class TwitterControlException extends RuntimeException {

    public TwitterStatusCode statusCode;

    public TwitterControlException(TwitterStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public TwitterControlException(TwitterStatusCode statusCode) {
        super();
        this.statusCode = statusCode;
    }
}
