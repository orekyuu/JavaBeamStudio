package net.orekyuu.javatter.api.service;

/**
 * サービス関連の例外
 * @since 1.0.0
 */
public final class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
