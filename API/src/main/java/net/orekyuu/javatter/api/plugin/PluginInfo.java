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

    public String getPluginId() {
        return pluginId;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorWebLink() {
        return authorWebLink;
    }

    public String getRepository() {
        return repository;
    }

    public String getSrcWebLink() {
        return srcWebLink;
    }

    public String getBugTrackWebLink() {
        return bugTrackWebLink;
    }

    public String getVersion() {
        return version;
    }

    public String getMain() {
        return main;
    }

    public String getPluginPage() {
        return pluginPage;
    }

    public String getApiVersion() {
        return apiVersion;
    }
}
