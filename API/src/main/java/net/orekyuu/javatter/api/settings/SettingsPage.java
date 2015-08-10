package net.orekyuu.javatter.api.settings;

/**
 * 設定ページのページ
 */
public final class SettingsPage {

    private String fxmlURL;
    private String title;

    public SettingsPage(String fxmlURL, String title) {
        this.fxmlURL = fxmlURL;
        this.title = title;
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

    public boolean isDummy() {
        return fxmlURL == null;
    }
}
