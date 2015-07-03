package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.service.CurrentTweetAreaService;
import net.orekyuu.javatter.api.service.TwitterUserService;
import net.orekyuu.javatter.api.twitter.TweetBuilder;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import java.io.File;
import java.util.Optional;

public class CurrentTweetAreaServiceImpl implements CurrentTweetAreaService {

    private Optional<Tweet> reply = Optional.empty();
    private StringBuilder text = new StringBuilder(140);
    private MutableList<File> medias = Lists.mutable.empty();

    private TwitterUserService userService = Lookup.lookup(TwitterUserService.class);

    @Override
    public void setReply(Tweet tweet) {
        fireChangeReply(() -> reply = Optional.ofNullable(tweet));
    }

    @Override
    public void setText(String text) {
        fireChangeText(() -> this.text = new StringBuilder(text));
    }

    @Override
    public void appendText(String text) {
        fireChangeText(() -> this.text.append(text));
    }

    @Override
    public void insertTop(String text) {
        fireChangeText(() -> {
            this.text = new StringBuilder(text).append(getText());
        });
    }

    @Override
    public String getText() {
        return text.toString();
    }

    @Override
    public void addMedia(File file) {
        fireChangeMedia(() -> medias.add(file));
    }

    @Override
    public void clearMedia() {
        fireChangeMedia(medias::clear);
    }

    @Override
    public ImmutableList<File> getAllMedias() {
        return medias.toImmutable();
    }

    @Override
    public void tweet() {
        userService.selectedAccount().ifPresent(user -> {
            TweetBuilder builder = user.createTweet().setAsync();
            reply.ifPresent(builder::replyTo);
            medias.each(builder::addFile);
            builder.setText(getText());
            builder.tweet();
            clear();
        });
    }


    private MutableList<ChangeListener<ImmutableList<File>>> mediaChangeListeners = Lists.mutable.empty();

    @Override
    public void addChangeMediaListener(ChangeListener<ImmutableList<File>> medias) {
        mediaChangeListeners.add(medias);
    }

    private MutableList<ChangeListener<Optional<Tweet>>> replyChangeListeners = Lists.mutable.empty();

    @Override
    public void addReplyChangeListener(ChangeListener<Optional<Tweet>> reply) {
        replyChangeListeners.add(reply);
    }

    private MutableList<ChangeListener<String>> textChangeListeners = Lists.mutable.empty();

    @Override
    public void addChangeTextListener(ChangeListener<String> text) {
        textChangeListeners.add(text);
    }

    @Override
    public void removeChangeMediaListener(ChangeListener<ImmutableList<File>> medias) {
        mediaChangeListeners.remove(medias);
    }

    @Override
    public void removeReplyChangeListener(ChangeListener<Optional<Tweet>> reply) {
        replyChangeListeners.remove(reply);
    }

    @Override
    public void removeChangeTextListener(ChangeListener<String> text) {
        textChangeListeners.remove(text);
    }

    private void fireChangeMedia(Runnable runnable) {
        ImmutableList<File> old = getAllMedias();
        runnable.run();

        ImmutableList<File> newValue = getAllMedias();
        if (!old.equals(newValue)) {
            mediaChangeListeners.each(listener -> listener.onChanged(old, newValue));
        }
    }

    private void fireChangeReply(Runnable runnable) {
        Optional<Tweet> old = reply;
        runnable.run();

        if (!old.equals(reply)) {
            replyChangeListeners.each(listener -> listener.onChanged(old, reply));
        }
    }

    private void fireChangeText(Runnable runnable) {
        String old = getText();
        runnable.run();

        String newValue = getText();
        if (!old.equals(newValue)) {
            textChangeListeners.each(listener -> listener.onChanged(old, getText()));
        }
    }

    @Override
    public void clear() {
        fireChangeText(() -> text = new StringBuilder(140));
        fireChangeReply(() -> reply = Optional.empty());
        fireChangeMedia(medias::clear);
    }
}
