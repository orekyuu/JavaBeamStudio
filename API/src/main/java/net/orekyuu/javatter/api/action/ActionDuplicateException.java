package net.orekyuu.javatter.api.action;

/**
 * アクションIDが重複した時にスローされる例外です
 */
public class ActionDuplicateException extends RuntimeException {

    public ActionDuplicateException(String message) {
        super(message);
    }
}
