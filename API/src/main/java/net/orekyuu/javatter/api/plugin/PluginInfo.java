package net.orekyuu.javatter.api.plugin;

import java.util.Objects;

/**
 * プラグインの情報
 * @since 1.0.0
 */
public final class PluginInfo {

    private final String pluginId;
    private final String pluginName;
    private final String author;
    private final String authorWebLink;
    private final String repository;
    private final String srcWebLink;
    private final String bugTrackWebLink;
    private final String version;
    private final String main;
    private final String pluginPage;
    private final String apiVersion;

    /**
     * @since 1.0.0
     * @param pluginId プラグインID
     * @param pluginName プラグイン名
     * @param author 作者名
     * @param authorWebLink 作者のWebサイト
     * @param repository リポジトリ
     * @param srcWebLink ソースのリンク
     * @param bugTrackWebLink バグ報告用のリンク
     * @param version バージョン
     * @param main プラグインのエントリポイント
     * @param pluginPage プラグインのページ
     * @param apiVersion 対応するAPIバージョン
     */
    public PluginInfo(String pluginId, String pluginName, String author, String authorWebLink, String repository,
                      String srcWebLink, String bugTrackWebLink, String version, String main, String pluginPage, String apiVersion) {
        Objects.requireNonNull(pluginId, "pluginId is null");
        Objects.requireNonNull(pluginName, "pluginName is null");
        Objects.requireNonNull(version, "version is null");
        Objects.requireNonNull(main, "main is null");
        Objects.requireNonNull(apiVersion, "apiVersion is null");

        this.pluginId = pluginId;
        this.pluginName = pluginName;
        this.author = author;
        this.authorWebLink = authorWebLink;
        this.repository = repository;
        this.srcWebLink = srcWebLink;
        this.bugTrackWebLink = bugTrackWebLink;
        this.version = version;
        this.main = main;
        this.pluginPage = pluginPage;
        this.apiVersion = apiVersion;
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
     * @return プラグイン名
     */
    public String getPluginName() {
        return pluginName;
    }

    /**
     * @since 1.0.0
     * @return 作者名
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @since 1.0.0
     * @return 作者のサイトのリンク
     */
    public String getAuthorWebLink() {
        return authorWebLink;
    }

    /**
     * @since 1.0.0
     * @return リポジトリのURL
     */
    public String getRepository() {
        return repository;
    }

    /**
     * @since 1.0.0
     * @return ソースのリンク
     */
    public String getSrcWebLink() {
        return srcWebLink;
    }

    /**
     * @since 1.0.0
     * @return バグ報告用のリンク
     */
    public String getBugTrackWebLink() {
        return bugTrackWebLink;
    }

    /**
     * @since 1.0.0
     * @return プラグインのバージョン
     */
    public String getVersion() {
        return version;
    }

    /**
     * @since 1.0.0
     * @return プラグインのエントリポイント
     */
    public String getMain() {
        return main;
    }

    /**
     * @since 1.0.0
     * @return プラグインのページ
     */
    public String getPluginPage() {
        return pluginPage;
    }

    /**
     * @since 1.0.0
     * @return 対応するAPIバージョン
     */
    public String getApiVersion() {
        return apiVersion;
    }
}
