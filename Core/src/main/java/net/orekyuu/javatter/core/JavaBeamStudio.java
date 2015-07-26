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
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.command.CommandManager;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.plugin.*;
import net.orekyuu.javatter.api.service.*;
import net.orekyuu.javatter.api.util.lookup.Lookup;
import net.orekyuu.javatter.api.util.lookup.Lookuper;
import net.orekyuu.javatter.core.column.ColumnInfos;
import net.orekyuu.javatter.core.column.HomeTimeLineColumn;
import net.orekyuu.javatter.core.column.MentionColumn;
import net.orekyuu.javatter.core.command.CommandManagerImpl;
import net.orekyuu.javatter.core.service.*;

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
        //プラグインのロード
        try {
            initPlugins();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //アカウントの初期化
        initAccount();
        registColumns();

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
    }

    private void initPlugins() throws IOException {
        PluginService pluginService = Lookup.lookup(PluginService.class);
        PluginClassLoader classLoader = new PluginClassLoader(ClassLoader.getSystemClassLoader());
        Path plugins = Paths.get("plugins");
        if (Files.notExists(plugins)) {
            Files.createDirectory(plugins);
        }
        ImmutableList<PluginInfo> list = pluginService.loadPlugins(plugins, classLoader);
        MutableList<Object> pluginClass = Lists.mutable.of();
        for (PluginInfo pluginInfo : list) {
            try {
                Class<?> loadClass = Class.forName(pluginInfo.getMain(), true, classLoader);
                Object instance = loadClass.getConstructor().newInstance();
                pluginClass.add(instance);
                Lookup.inject(instance);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }

        callAnnotateMethod(pluginClass, OnInit.class);
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
                    bind(CurrentTweetAreaService.class).to(CurrentTweetAreaServiceImpl.class).in(Singleton.class);
                    bind(ColumnStateStorageService.class).to(ColumnStateStorageServiceImpl.class);
                    bind(PluginService.class).to(PluginServiceImpl.class);
                    bind(CommandManager.class).to(CommandManagerImpl.class).in(Singleton.class);
                    bind(EnvironmentService.class).to(EnvironmentServiceImpl.class);
                    bind(UserWindowService.class).to(UserWindowServiceImpl.class);
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
        columnManager.registerColumn(ColumnInfos.PLUGIN_ID_BUILDIN, HomeTimeLineColumn.ID, "/columns/home.fxml", "タイムライン");
        columnManager.registerColumn(ColumnInfos.PLUGIN_ID_BUILDIN, MentionColumn.ID, "/columns/mention.fxml", "メンション");
    }
}
