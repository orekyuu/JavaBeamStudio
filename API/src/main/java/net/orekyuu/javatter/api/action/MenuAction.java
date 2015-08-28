package net.orekyuu.javatter.api.action;

import javafx.scene.input.KeyCombination;

/**
 * メニューバーのアクション
 */
public final class MenuAction {

    private final String pluginId;
    private final String actionName;
    private final ActionListener<MenuActionEvent> event;
    private final KeyCombination keyCombination;
    private final String actionId;

    private MenuAction(String pluginId, String actionName, ActionListener<MenuActionEvent> event, KeyCombination keyCombination, String actionId) {
        this.pluginId = pluginId;
        this.actionName = actionName;
        this.event = event;
        this.keyCombination = keyCombination;
        this.actionId = actionId;
    }

    /**
     * @since 1.0.0
     * @return プラグインID
     */
    public String getPluginId() {
        return pluginId;
    }

    /**
     * @since 1.0.0
     * @return アクション名
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * @since 1.0.0
     * @return アクションリスナ
     */
    public ActionListener<MenuActionEvent> getEvent() {
        return event;
    }

    /**
     * @since 1.0.0
     * @return ショートカット
     */
    public KeyCombination getDefaultShortcut() {
        return keyCombination;
    }

    /**
     * @since 1.0.0
     * @return ActionID
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * アクションを作成するビルダー
     */
    public static class Builder {
        private String pluginId;
        private String actionName;
        private ActionListener<MenuActionEvent> event;
        private KeyCombination keyCombination;
        private String actionId;

        /**
         * @since 1.0.0
         * @param pluginId プラグインID
         */
        public Builder(String pluginId, String actionId) {
            this.pluginId = pluginId;
            this.actionId = actionId;
        }

        /**
         * アクションの表示名を設定します。
         * @since 1.0.0
         * @param actionName アクションの表示名
         * @return メソッドチェーンのためのthis
         */
        public Builder actionName(String actionName) {
            this.actionName = actionName;
            return this;
        }

        /**
         * アクションリスナを設定します。
         * @since 1.0.0
         * @param event アクションリスナ
         * @return メソッドチェーンのためのthis
         */
        public Builder action(ActionListener<MenuActionEvent> event) {
            this.event = event;
            return this;
        }

        /**
         * デフォルトのショートカットキーを設定します。
         * @since 1.0.0
         * @param shortcut ショートカットキー
         * @return メソッドチェーンのためのthis
         */
        public Builder defaultShortcut(KeyCombination shortcut) {
            this.keyCombination = shortcut;
            return this;
        }

        /**
         * 設定された内容でMenuActionを作成します。
         * @since 1.0.0
         * @return MenuAction
         */
        public MenuAction build() {
            if (event == null) {
                event = e -> {};
            }
            if (actionName == null) {
                actionName = "";
            }

            return new MenuAction(pluginId, actionName, event, keyCombination, actionId);
        }
    }
}
