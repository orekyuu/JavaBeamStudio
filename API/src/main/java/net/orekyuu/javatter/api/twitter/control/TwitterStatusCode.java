package net.orekyuu.javatter.api.twitter.control;

public enum TwitterStatusCode {

    OK(200),
    NOT_MODIFIED(304),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    NOT_ACCEPTABLE(406),
    ENHANCE_YOUR_CALM(420),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503);

    public final int code;

    TwitterStatusCode(int code) {
        this.code = code;
    }

    public static TwitterStatusCode fromStatusCode(int statusCode) {
        for (TwitterStatusCode twitterStatusCode : values()) {
            if (twitterStatusCode.code == statusCode) {
                return twitterStatusCode;
            }
        }
        throw new IllegalArgumentException(statusCode + " is not found.");
    }
}
