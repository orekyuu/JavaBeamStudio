package net.orekyuu.javatter.core.twitter.model;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.fixed.ArrayAdapter;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.media.Media;
import net.orekyuu.javatter.api.twitter.model.Mention;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.core.cache.FavoriteCache;
import net.orekyuu.javatter.core.cache.RetweetCache;
import net.orekyuu.javatter.core.cache.TweetCache;
import twitter4j.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetImpl implements Tweet {

    private final long statusId;
    private final String text;
    private final LocalDateTime createdAt;
    private final long replyStatusId;
    private final String viaName;
    private final String viaLink;
    private final Tweet retweetFrom;
    private final User owner;
    private final UserMentionEntity[] mentions;
    private final URLEntity[] urls;
    private final HashtagEntity[] hashtags;
    private final MediaEntity[] medias;

    private static final Pattern viaNamePattern = Pattern.compile("^<a.*>(.*)</a>$");
    private static final Pattern viaLinkPattern = Pattern.compile("^<a.*href=\"(.*?)\".*>.*</a>$");

    private TweetImpl(twitter4j.Status status) {
        statusId = status.getId();
        text = status.getText();
        createdAt = LocalDateTime.ofInstant(status.getCreatedAt().toInstant(), ZoneId.systemDefault());
        replyStatusId = status.getInReplyToStatusId();
        String via = status.getSource();
        Matcher matcher = viaNamePattern.matcher(via);
        mentions = status.getUserMentionEntities();
        urls = status.getURLEntities();
        hashtags = status.getHashtagEntities();
        medias = status.getExtendedMediaEntities();
        if (matcher.find()) {
            viaName = matcher.group(1);
        } else {
            viaName = "error";
        }
        Matcher matcher2 = viaLinkPattern.matcher(via);
        if (matcher2.find()) {
            viaLink = matcher2.group(1);
        } else {
            viaLink = "error";
        }
        Status retweetedStatus = status.getRetweetedStatus();
        retweetFrom = retweetedStatus != null ? TweetImpl.create(retweetedStatus) : null;
        owner = UserImpl.create(status.getUser());
    }

    /**
     * @return ツイートID
     * @since 1.0.0
     */
    @Override
    public long getStatusId() {
        return statusId;
    }

    /**
     * @return ツイートのテキスト
     * @since 1.0.0
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * @return ツイートされた日時
     * @since 1.0.0
     */
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return リプライ先
     * @since 1.0.0
     */
    @Override
    public long getReplyStatusId() {
        return replyStatusId;
    }

    /**
     * @return ツイートされたクライアント名
     * @since 1.0.0
     */
    @Override
    public String getViaName() {
        return viaName;
    }

    /**
     * @return クライアントのリンク
     * @since 1.0.0
     */
    @Override
    public String getViaLink() {
        return viaLink;
    }

    /**
     * @return リツイート元のツイート
     * @since 1.0.0
     */
    @Override
    public Tweet getRetweetFrom() {
        return retweetFrom;
    }

    /**
     * @return ツイートしたユーザー
     * @since 1.0.0
     */
    @Override
    public User getOwner() {
        return owner;
    }

    /**
     * @return このツイートをリツイートしたか
     * @since 1.0.0
     */
    @Override
    public boolean isRetweeted(TwitterUser user) {
        return RetweetCache.getInstance().isRetweeted(user, this);
    }

    public UserMentionEntity[] getMentions() {
        return mentions;
    }

    public URLEntity[] getUrls() {
        return urls;
    }

    public HashtagEntity[] getHashtags() {
        return hashtags;
    }

    public MediaEntity[] getMedias() {
        return medias;
    }

    /**
     * @return このツイートをお気に入りしたか
     * @since 1.0.0
     */
    @Override
    public boolean isFavorited(TwitterUser account) {
        return FavoriteCache.getInstance().isFavorited(account, this);
    }

    @Override
    public ImmutableList<String> medias() {
        MutableList<String> urls = ArrayAdapter.adapt(medias).collect(MediaEntity::getMediaURL);
        return urls.toImmutable();
    }

    @Override
    public ImmutableList<? extends Mention> mentions() {
        return ArrayAdapter.adapt(mentions)
                .collect(MentionImpl::new)
                .toImmutable();
    }

    @Override
    public ImmutableList<Media> mediaList() {
        MutableList<Media> collect = ArrayAdapter.adapt(medias).collect(e -> {
            Media.Builder builder = new Media.Builder();
            String type = e.getType();
            if (type.equals("photo")) {
                builder.setContentUrl(e.getMediaURL());
                builder.setPreviewImageUrl(e.getMediaURL());
                builder.setType(Media.Type.IMAGE);
            } else {
                ExtendedMediaEntity entity = (ExtendedMediaEntity) e;
                builder.setPreviewImageUrl(e.getMediaURL());
                builder.setContentUrl(e.getMediaURL());
                builder.setType(Media.Type.IMAGE);
                //動画があるか
                for (ExtendedMediaEntity.Variant variant : entity.getVideoVariants()) {
                    builder.setType(Media.Type.MOVIE);
                    builder.setContentUrl("http" + variant.getUrl().substring(5));
                    //一回ループしたら動画があるので抜ける
                    break;
                }
            }
            return builder.build();
        });
        return collect.toImmutable();
    }

    public static Tweet create(twitter4j.Status status) {
        TweetCache tweetCache = TweetCache.getInstance();
        TweetImpl tweet = new TweetImpl(status);
        tweetCache.update(tweet);
        return tweet;
    }
}
