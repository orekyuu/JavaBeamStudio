package net.orekyuu.javatter.api.twitter.media;

import java.util.Objects;

/**
 * メディアの情報
 * @since 1.0.2
 */
public final class Media {

    private final String previewImageUrl;
    private final String contentUrl;
    private final Type type;

    /**
     * メディアのタイプ
     * @since 1.0.2
     */
    public enum Type {
        IMAGE, MOVIE
    }

    private Media(String previewImageUrl, String contentUrl, Type type) {
        this.previewImageUrl = previewImageUrl;
        this.contentUrl = contentUrl;
        this.type = type;
    }

    /**
     * プレビューの画像のURL
     * @return プレビューの画像のURL
     * @since 1.0.2
     */
    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    /**
     * コンテンツの内容
     * @return コンテンツの内容のURL
     * @since 1.0.2
     */
    public String getContentUrl() {
        return contentUrl;
    }

    /**
     * メディアのタイプ
     * @return メディアのタイプ
     * @since 1.0.2
     */
    public Type getType() {
        return type;
    }

    /**
     * メディアを作成するビルダー
     * @since 1.0.2
     */
    public static final class Builder {
        private String previewImageUrl;
        private String contentUrl;
        private Type type;

        /**
         * プレビューの画像
         * @param previewImageUrl プレビューの画像のurl
         * @return this
         * @since 1.0.2
         */
        public Builder setPreviewImageUrl(String previewImageUrl) {
            this.previewImageUrl = previewImageUrl;
            return this;
        }

        /**
         * コンテンツ
         * @param contentUrl コンテンツのURL
         * @return this
         * @since 1.0.2
         */
        public Builder setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
            return this;
        }

        /**
         * タイプ
         * @param type メディアのタイプ
         * @return this
         * @since 1.0.2
         */
        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        /**
         * 指定されたパラメータからMediaを作成します。
         * @return Media
         * @since 1.0.2
         */
        public Media build() {
            Objects.requireNonNull(previewImageUrl);
            Objects.requireNonNull(contentUrl);
            Objects.requireNonNull(type);

            return new Media(previewImageUrl, contentUrl, type);
        }
    }
}
