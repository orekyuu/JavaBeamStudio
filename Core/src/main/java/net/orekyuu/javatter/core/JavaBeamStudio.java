package net.orekyuu.javatter.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.service.*;
import net.orekyuu.javatter.api.util.lookup.Lookup;
import net.orekyuu.javatter.api.util.lookup.Lookuper;
import net.orekyuu.javatter.core.column.ColumnInfos;
import net.orekyuu.javatter.core.column.HomeTimeLineColumn;
import net.orekyuu.javatter.core.service.*;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;

public class JavaBeamStudio extends Application {
    @Override
    public void start(Stage primaryStage) {
        //DIの設定は必ず最初に行うこと！
        //初期化しないとDIが使えない
        initDISettings();
        registColumns();


        URL resource = getClass().getResource("/layout/main.fxml");
        JavatterFXMLLoader loader = new JavatterFXMLLoader(resource);
        try {
            loader.setOwnerStage(primaryStage);
            Parent parent = loader.load();
            primaryStage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();
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
                    bind(ColumnStateStorageService.class).to(ColumnStateStorageServiceImpl.class);
                }
            });

            @Override
            public <T> T lookup(Class<T> clazz) {
                return injector.getInstance(clazz);
            }

            @Override
            public void inject(Object object) {
                injector.injectMembers(object);
            }
        });
    }

    private void registColumns() {
        ColumnManager columnManager = Lookup.lookup(ColumnManager.class);
        //タイムラインを登録
        columnManager.registerColumn(ColumnInfos.PLUGIN_ID_BUILDIN, HomeTimeLineColumn.ID, "/columns/home.fxml");
    }
}
