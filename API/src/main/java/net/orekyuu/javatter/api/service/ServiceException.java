package net.orekyuu.javatter.api.service;

/**
 * サービス関連の例外
 * @since 1.0.0
 */
public final class ServiceException extends RuntimeException {

    /**
     * メッセージ付きの例外を生成します
     * @since 1.0.0
     * @param message message
     */
    public ServiceException(String message) {
        super(message);
    }
}
