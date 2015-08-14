package net.orekyuu.javatter.api.plugin;

import java.util.Objects;

/**
 * プラグインの情報
 * @since 1.0.0
 */
public final class PluginInfo {

    private String pluginId;
    private String pluginName;
    private String author;
    private String authorWebLink;
    private String repository;
    private String srcWebLink;
    private String bugTrackWebLink;
    private String version;
    private String main;

    public PluginInfo(String pluginId, String pluginName, String author, String authorWebLink, String repository,
                      String srcWebLink, String bugTrackWebLink, String version, String main) {
        Objects.requireNonNull(pluginId, "pluginId is null");
        Objects.requireNonNull(pluginName, "pluginName is null");
        Objects.requireNonNull(version, "version is null");
        Objects.requireNonNull(main, "main is null");

        this.pluginId = pluginId;
        this.pluginName = pluginName;
        this.author = author;
        this.authorWebLink = authorWebLink;
        this.repository = repository;
        this.srcWebLink = srcWebLink;
        this.bugTrackWebLink = bugTrackWebLink;
        this.version = version;
        this.main = main;
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
}
