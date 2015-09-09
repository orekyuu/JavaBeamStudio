package net.orekyuu.javatter.api.action;

/**
 * アクションIDが重複した時にスローされる例外です
 * @since 1.0.0
 */
public class ActionDuplicateException extends RuntimeException {

    /**
     * メッセージ付きの例外を作成します
     * @param message message
     */
    public ActionDuplicateException(String message) {
        super(message);
    }
}
