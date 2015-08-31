package net.orekyuu.javatter.api.plugin;

/**
 * プラグインが見つからなかった時にthrowされる例外です。
 * @since 1.0.0
 */
public final class PluginNotFoundException extends RuntimeException {

    /**
     * @since 1.0.0
     * @param message エラーメッセージ
     */
    public PluginNotFoundException(String message) {
        super(message);
    }
}
