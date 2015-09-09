package net.orekyuu.javatter.api.userwindow;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.service.Service;

import java.net.URL;

/**
 * ユーザーウィンドウタブを管理するクラスです。
 * @since 1.0.0
 */
@Service
public interface UserWindowTabManager {

    /**
     * ユーザーウィンドウのタブを返します。
     * FXML Controllerは必ず{@link UserWindowTab}を実装している必要があります。
     * @since 1.0.0
     * @param fxmlPath FXMLのパス
     */
    void registerTab(String fxmlPath);

    /**
     * @since 1.0.0
     * @return FXMLのリスト
     */
    ImmutableList<URL> registeredTabFxmlURLs();
}
