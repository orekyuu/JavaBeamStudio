package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.io.File;
import java.util.Optional;

@Service
public interface CurrentTweetAreaService {

    void setReply(Tweet tweet);

    void setText(String text);

    void appendText(String text);

    void insertTop(String text);

    String getText();

    void addMedia(File file);

    void clearMedia();

    ImmutableList<File> getAllMedias();

    void tweet();

    void addChangeMediaListener(ChangeListener<ImmutableList<File>> medias);

    void addReplyChangeListener(ChangeListener<Optional<Tweet>> reply);

    void addChangeTextListener(ChangeListener<String> text);

    void removeChangeMediaListener(ChangeListener<ImmutableList<File>> medias);

    void removeReplyChangeListener(ChangeListener<Optional<Tweet>> reply);

    void removeChangeTextListener(ChangeListener<String> text);

    void clear();

    interface ChangeListener<T> {
        void onChanged(T oldValue, T newValue);
    }
}
