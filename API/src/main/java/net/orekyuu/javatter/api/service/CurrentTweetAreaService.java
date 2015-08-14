package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.io.File;
import java.util.Optional;

/**
 * ツイートを行うテキストエリアを操作するサービス
 */
@Service
public interface CurrentTweetAreaService {

    /**
     * リプライ先を設定します。
     * @param tweet リプライ先
     * @since 1.0.0
     */
    void setReply(Tweet tweet);

    /**
     * テキストを設定します
     * @param text テキスト
     * @since 1.0.0
     */
    void setText(String text);

    /**
     * テキストを最後に追加します。
     * @param text テキスト
     * @since 1.0.0
     */
    void appendText(String text);

    /**
     * テキストを先頭に追加します。
     * @since 1.0.0
     * @param text 先頭に追加するテキスト
     */
    void insertTop(String text);

    /**
     * ツイートエリアに記入されている文字列を返します。
     * @since 1.0.0
     * @return ツイートエリアに記入されているテキスト
     */
    String getText();

    /**
     * 投稿画像を追加します
     * @since 1.0.0
     * @param file 画像ファイル
     */
    void addMedia(File file);

    /**
     * 投稿画像をクリアします
     * @since 1.0.0
     */
    void clearMedia();

    /**
     * 設定されている全てのメディアを返します。
     * @since 1.0.0
     * @return 全てのメディアを返します
     */
    ImmutableList<File> getAllMedias();

    /**
     * 設定されている内容でツイートします。
     * @since 1.0.0
     */
    void tweet();

    /**
     * メディアが変更された時のリスナを追加します。
     * @since 1.0.0
     * @param medias メディアが変更された時のリスナ。
     */
    void addChangeMediaListener(ChangeListener<ImmutableList<File>> medias);

    /**
     * リプライ先が変更された時のリスナを追加します。
     * @since 1.0.0
     * @param reply リプライ先が変更された時のリスナ
     */
    void addReplyChangeListener(ChangeListener<Optional<Tweet>> reply);

    /**
     * テキストが変更された時のリスナを追加します。
     * @since 1.0.0
     * @param text テキストが変更された時のリスナ
     */
    void addChangeTextListener(ChangeListener<String> text);

    /**
     * メディアが変更された時のリスナを削除します。
     * @since 1.0.0
     * @param medias メディアが変更された時のリスナ。
     */
    void removeChangeMediaListener(ChangeListener<ImmutableList<File>> medias);

    /**
     * リプライ先が変更された時のリスナを削除します。
     * @since 1.0.0
     * @param reply リプライ先が変更された時のリスナ
     */
    void removeReplyChangeListener(ChangeListener<Optional<Tweet>> reply);

    /**
     * テキストが変更された時のリスナを削除します。
     * @since 1.0.0
     * @param text テキストが変更された時のリスナ
     */
    void removeChangeTextListener(ChangeListener<String> text);

    /**
     * テキスト・メディア・リプライ先を初期値に戻します。
     * @since 1.0.0
     */
    void clear();

    interface ChangeListener<T> {
        void onChanged(T oldValue, T newValue);
    }
}
