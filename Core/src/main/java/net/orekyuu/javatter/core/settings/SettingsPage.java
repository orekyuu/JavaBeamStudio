package net.orekyuu.javatter.core.settings;

/**
 * 設定ページのページ
 */
public final class SettingsPage {

    private String fxmlURL;
    private String title;
    private String mainClass;

    public SettingsPage(String fxmlURL, String title) {
        this.fxmlURL = fxmlURL;
        this.title = title;
    }

    public SettingsPage(String fxmlURL, String title, String mainClass) {
        this.fxmlURL = fxmlURL;
        this.title = title;
        this.mainClass = mainClass;
    }

    public SettingsPage(String title) {
        this.title = title;
    }

    /**
     * @return FXMLのURL
     */
    public String getFxmlURL() {
        return fxmlURL;
    }

    /**
     * @return ページのタイトル
     */
    public String getTitle() {
        return title;
    }

    public String getMainClass() {
        return mainClass;
    }

    public boolean isDummy() {
        return fxmlURL == null;
    }
}
