package net.orekyuu.javatter.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.orekyuu.javatter.api.account.AccountStorageService;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.action.ActionManager;
import net.orekyuu.javatter.api.column.ColumnManager;
import net.orekyuu.javatter.api.column.ColumnService;
import net.orekyuu.javatter.api.column.ColumnStateStorageService;
import net.orekyuu.javatter.api.command.CommandManager;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.notification.NotificationService;
import net.orekyuu.javatter.api.plugin.OnInit;
import net.orekyuu.javatter.api.plugin.OnPostInit;
import net.orekyuu.javatter.api.plugin.PluginInfo;
import net.orekyuu.javatter.api.plugin.PluginService;
import net.orekyuu.javatter.api.service.*;
import net.orekyuu.javatter.api.storage.DataStorageService;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.userstream.OnMention;
import net.orekyuu.javatter.api.twitter.userstream.UserStream;
import net.orekyuu.javatter.api.twitter.userstream.events.*;
import net.orekyuu.javatter.api.userwindow.UserWindowService;
import net.orekyuu.javatter.api.userwindow.UserWindowTabManager;
import net.orekyuu.javatter.api.util.lookup.Lookup;
import net.orekyuu.javatter.api.util.lookup.Lookuper;
import net.orekyuu.javatter.core.action.ActionConfig;
import net.orekyuu.javatter.core.action.ActionManagerImpl;
import net.orekyuu.javatter.core.column.HomeTimeLineColumn;
import net.orekyuu.javatter.core.column.MentionColumn;
import net.orekyuu.javatter.core.command.CommandManagerImpl;
import net.orekyuu.javatter.core.notification.NotificationServiceImpl;
import net.orekyuu.javatter.core.service.*;
import net.orekyuu.javatter.core.storage.DataStorageServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaBeamStudio extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene waitingScene = new Scene(new Label("起動中"), 300, 100);
        primaryStage.setScene(waitingScene);
        primaryStage.show();
        //DIの設定は必ず最初に行うこと！
        //初期化しないとDIが使えない
        initDISettings();

        Runnable task = () -> {
            //アカウントの初期化
            initAccount();
            registerNotification();
            registColumns();
            initUserWindowTab();
            ActionConfig.initialize();

            URL resource = getClass().getResource("/layout/main.fxml");
            JavatterFXMLLoader loader = new JavatterFXMLLoader(resource);
            try {
                loader.setOwnerStage(primaryStage);
                Parent parent = loader.load();
                primaryStage.setScene(new Scene(parent));
                primaryStage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        //プラグインのロード
        try {
            initPlugins(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPlugins(Runnable initializeTask) throws IOException {
        PluginService pluginService = Lookup.lookup(PluginService.class);
        Path plugins = Paths.get("plugins");
        if (Files.notExists(plugins)) {
            Files.createDirectory(plugins);
        }
        ImmutableList<PluginInfo> list = pluginService.loadPlugins(plugins);
        MutableList<Object> pluginClass = Lists.mutable.of();
        for (PluginInfo pluginInfo : list) {
            //ビルドインは予約のためだけなので実際にはロードしない
            if (pluginInfo.getPluginId().equals(PluginServiceImpl.BUILD_IN.getPluginId())) {
                continue;
            }
            try {
                Class<?> loadClass = Class.forName(pluginInfo.getMain(), true, pluginService.getPluginClassLoader());
                Object instance = loadClass.getConstructor().newInstance();
                pluginClass.add(instance);
                Lookup.inject(instance);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }

        callAnnotateMethod(pluginClass, OnInit.class);
        initializeTask.run();
        callAnnotateMethod(pluginClass, OnPostInit.class);
    }

    private void callAnnotateMethod(MutableList<Object> plugins, Class<? extends Annotation> annotationClass) {
        for (Object plugin : plugins) {
            for (Method method : plugin.getClass().getDeclaredMethods()) {
                if (method.getAnnotation(annotationClass) == null) {
                    continue;
                }
                try {
                    method.invoke(plugin);
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initUserWindowTab() {
        UserWindowTabManager manager = Lookup.lookup(UserWindowTabManager.class);
        manager.registerTab("/layout/userInfo.fxml");
        manager.registerTab("/layout/user_timeline.fxml");
        manager.registerTab("/layout/userFavorite.fxml");
        manager.registerTab("/layout/userFollows.fxml");
        manager.registerTab("/layout/userFollowers.fxml");
    }

    private void initAccount() {
        AccountStorageService accountStorageService = Lookup.lookup(AccountStorageService.class);
        TwitterUserService twitterUserService = Lookup.lookup(TwitterUserService.class);
        //保存されてるTwitterアカウントを取得
        ImmutableList<TwitterAccount> twitterAccounts = accountStorageService.findByType(TwitterAccount.class);
        //全てのアカウントを認証
        twitterAccounts.each(twitterUserService::register);
    }

    private void initDISettings() {

        Lookup.setLookuper(new Lookuper() {
            Injector injector = Guice.createInjector(new AbstractModule() {
                @Override
                protected void configure() {
                    bind(AccountStorageService.class).to(AccountStorageServiceImpl.class);
                    bind(TwitterUserService.class).to(TwitterUserServiceImpl.class).in(Singleton.class);
                    bind(ColumnManager.class).to(ColumnManagerImpl.class).in(Singleton.class);
                    bind(ColumnService.class).to(ColumnServiceImpl.class).in(Singleton.class);
                    bind(UserIconStorage.class).to(UserIconStorageImpl.class).in(Singleton.class);
                    bind(MainTweetAreaService.class).to(MainTweetAreaServiceImpl.class).in(Singleton.class);
                    bind(ColumnStateStorageService.class).to(ColumnStateStorageServiceImpl.class);
                    bind(PluginService.class).to(PluginServiceImpl.class);
                    bind(CommandManager.class).to(CommandManagerImpl.class).in(Singleton.class);
                    bind(EnvironmentService.class).to(EnvironmentServiceImpl.class);
                    bind(UserWindowService.class).to(UserWindowServiceImpl.class);
                    bind(ApplicationService.class).toInstance(new ApplicationServiceImpl(JavaBeamStudio.this));
                    bind(NotificationService.class).to(NotificationServiceImpl.class);
                    bind(DataStorageService.class).to(DataStorageServiceImpl.class);
                    bind(UserWindowTabManager.class).to(UserWindowTabManagerImpl.class).in(Singleton.class);
                    bind(ActionManager.class).to(ActionManagerImpl.class).in(Singleton.class);
                }
            });

            @Override
            public <T> T lookup(Class<T> clazz) {
                if (!clazz.isAnnotationPresent(Service.class)) {
                    throw new ServiceException(clazz.getName());
                }
                return injector.getInstance(clazz);
            }

            @Override
            public void inject(Object object) {
                for (Field field : object.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Inject.class) && !field.getType()
                            .isAnnotationPresent(Service.class)) {
                        throw new ServiceException(object.getClass().getName() + "#" + field.getType().getName());
                    }
                }
                injector.injectMembers(object);
            }
        });
    }

    private void registColumns() {
        ColumnManager columnManager = Lookup.lookup(ColumnManager.class);
        //タイムラインを登録
        columnManager.registerColumn(PluginServiceImpl.BUILD_IN.getPluginId(), HomeTimeLineColumn.ID, "/columns/home.fxml", "タイムライン");
        columnManager.registerColumn(PluginServiceImpl.BUILD_IN.getPluginId(), MentionColumn.ID, "/columns/mention.fxml", "メンション");
    }

    private OnFavorite onFavorite;
    private OnUnfavorite onUnfavorite;
    private OnMention onMention;
    private OnStatus onStatus;
    private OnFollow onFollow;
    private OnUnfollow onUnfollow;
    private void registerNotification() {
        TwitterUserService service = Lookup.lookup(TwitterUserService.class);
        NotificationService notification = Lookup.lookup(NotificationService.class);
        UserIconStorage iconStorage = Lookup.lookup(UserIconStorage.class);
        service.allUser().each(user -> {
            UserStream stream = user.userStream();
            //お気に入り
            onFavorite = new OnFavorite() {
                @Override
                public void onFavorite(User source, User target, Tweet tweet) {
                    if (source.getId() == user.getUser().getId()) {
                        return;
                    }
                    notification.create()
                            .title("@" + source.getScreenName() + "にふぁぼられました")
                            .text(tweet.getText())
                            .icon(iconStorage.find(source))
                            .show();
                }
            };
            stream.onFavorite(onFavorite);
            //あんふぁぼ
            onUnfavorite = new OnUnfavorite() {
                @Override
                public void onUnfavorite(User source, User target, Tweet tweet) {
                    if (source.getId() == user.getUser().getId()) {
                        return;
                    }
                    notification.create()
                            .title("@" + source.getScreenName() + "にあんふぁぼされました")
                            .text(tweet.getText())
                            .icon(iconStorage.find(source))
                            .show();
                }
            };
            stream.onUnfavorite(onUnfavorite);
            //メンション
            onMention = new OnMention() {
                @Override
                public void onMention(Tweet tweet, User from) {
                    notification.create()
                            .title("@" + from.getScreenName() + "からメンション")
                            .text(tweet.getText())
                            .icon(iconStorage.find(from))
                            .show();
                }
            };
            stream.onMention(onMention);
            //RT
            onStatus = new OnStatus() {
                @Override
                public void onStatus(Tweet tweet) {
                    Tweet retweetFrom = tweet.getRetweetFrom();
                    if (retweetFrom != null && retweetFrom.getOwner().getId() == user.getUser().getId()) {
                        notification.create()
                                .title("RTされました")
                                .text(retweetFrom.getText())
                                .icon(iconStorage.find(tweet.getOwner()))
                                .show();
                    }
                }
            };
            stream.onStatus(onStatus);
            //フォロー
            onFollow = new OnFollow() {
                @Override
                public void onFollow(User source, User followedUser) {
                    if (followedUser.getId() == user.getUser().getId()) {
                        notification.create()
                                .title("@" + source.getScreenName() + "にフォローされました")
                                .text(source.getDescription())
                                .icon(iconStorage.find(source))
                                .setAction(() -> Lookup.lookup(UserWindowService.class).open(source, user))
                                .show();
                    }
                }
            };
            stream.onFollow(onFollow);
            //アンフォロー
            onUnfollow = new OnUnfollow() {
                @Override
                public void onUnfollow(User source, User unfollowedUser) {
                    if (unfollowedUser.getId() == user.getUser().getId()) {

                        notification.create()
                                .title("@" + source.getScreenName() + "にリムられました")
                                .text(source.getDescription())
                                .icon(iconStorage.find(source))
                                .setAction(() -> Lookup.lookup(UserWindowService.class).open(source, user))
                                .show();
                    }
                }
            };
            stream.onUnfollow(onUnfollow);
        });
    }
}
