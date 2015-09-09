package net.orekyuu.javatter.api.action;

/**
 * ツイートセルのアクション
 * @since 1.0.0
 */
public final class TweetCellAction {

    private final String pluginId;
    private final String actionName;
    private final ActionListener<TweetCellActionEvent> event;
    private final String actionId;

    private TweetCellAction(String pluginId, String actionName, ActionListener<TweetCellActionEvent> event, String actionId) {
        this.pluginId = pluginId;
        this.actionName = actionName;
        this.event = event;
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
    public ActionListener<TweetCellActionEvent> getEvent() {
        return event;
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
     * @since 1.0.0
     */
    public final static class Builder {
        private String pluginId;
        private String actionName;
        private ActionListener<TweetCellActionEvent> event;
        private String actionId;

        /**
         * @since 1.0.0
         * @param pluginId プラグインID
         * @param actionId アクションID
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
        public Builder action(ActionListener<TweetCellActionEvent> event) {
            this.event = event;
            return this;
        }

        /**
         * 設定された内容でTweetCellActionを作成します
         * @since 1.0.0
         * @return TweetCellAction
         */
        public TweetCellAction build() {
            return new TweetCellAction(pluginId, actionName, event, actionId);
        }
    }
}
