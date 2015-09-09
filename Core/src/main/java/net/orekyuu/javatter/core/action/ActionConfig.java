package net.orekyuu.javatter.core.action;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.*;
import net.orekyuu.javatter.api.action.ActionManager;
import net.orekyuu.javatter.api.action.MenuAction;
import net.orekyuu.javatter.api.action.TweetCellAction;
import net.orekyuu.javatter.api.command.CommandManager;
import net.orekyuu.javatter.api.service.ApplicationService;
import net.orekyuu.javatter.api.service.MainTweetAreaService;
import net.orekyuu.javatter.api.storage.DataStorageService;
import net.orekyuu.javatter.api.util.lookup.Lookup;
import net.orekyuu.javatter.core.service.PluginServiceImpl;
import net.orekyuu.javatter.core.settings.storage.GeneralSetting;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class ActionConfig {

    public static void initialize() {
        ActionManager manager = Lookup.lookup(ActionManager.class);
        //ツイートアクション
        manager.registerMenuAction(new MenuAction.Builder(PluginServiceImpl.BUILD_IN.getPluginId(), EnumActionID.TWEET_ACTION.getId())
                .defaultShortcut(new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN))
                .actionName("ツイート")
                .action(e -> {
                    DataStorageService storageService = Lookup.lookup(DataStorageService.class);
                    MainTweetAreaService mainTweetAreaService = Lookup.lookup(MainTweetAreaService.class);
                    GeneralSetting setting = storageService
                            .find(PluginServiceImpl.BUILD_IN.getPluginId(), GeneralSetting.class, new GeneralSetting());
                    if (setting.isCheckTweet()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "ツイートしますか？", ButtonType.YES, ButtonType.NO);
                        alert.setTitle("確認");
                        Optional<ButtonType> result = alert.showAndWait();
                        result.filter(t -> t == ButtonType.YES).ifPresent(t -> mainTweetAreaService.tweet());
                    } else {
                        mainTweetAreaService.tweet();
                    }
                }).build());
        //コマンドアクション
        manager.registerMenuAction(new MenuAction.Builder(PluginServiceImpl.BUILD_IN.getPluginId(), EnumActionID.COMMAND.getId())
                .defaultShortcut(new KeyCodeCombination(KeyCode.SPACE, KeyCombination.SHORTCUT_DOWN))
                .actionName("コマンド実行")
                .action(e -> {
                    MainTweetAreaService mainTweetAreaService = Lookup.lookup(MainTweetAreaService.class);
                    CommandManager commandManager = Lookup.lookup(CommandManager.class);
                    String text = mainTweetAreaService.getText();
                    String s = commandManager.execCommand(mainTweetAreaService.getText());
                    //コマンドによってテキストが書き換えられなかった場合のみ実行
                    if (mainTweetAreaService.getText().equals(text)) {
                        mainTweetAreaService.setText(s);
                    }
                }).build());
        //ツイートをブラウザで開く
        manager.registerTweetCellAction(new TweetCellAction.Builder(PluginServiceImpl.BUILD_IN.getPluginId(), EnumActionID.TWEET_OPEN_BROWSER.getId())
                .actionName("ブラウザで開く")
                .action(e -> {
                    try {
                        ApplicationService applicationService = Lookup.lookup(ApplicationService.class);
                        String url = String.format("https://twitter.com/%s/status/%d",
                                e.getTweet().getOwner().getScreenName(), e.getTweet().getStatusId());
                        applicationService.getApplication()
                                .getHostServices()
                                .showDocument(new URL(url).toURI().toString());
                    } catch (URISyntaxException | MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                })
                .build());

        manager.registerTweetCellAction(new TweetCellAction.Builder(PluginServiceImpl.BUILD_IN
                .getPluginId(), EnumActionID.FAV_RT.getId())
                .actionName("ふぁぼ＆RT")
                .action(e -> {
                    e.getOwner().favoriteAsync(e.getTweet());
                    e.getOwner().retweetAsync(e.getTweet());
                })
                .build());

        manager.registerTweetCellAction(new TweetCellAction.Builder(PluginServiceImpl.BUILD_IN.getPluginId(), EnumActionID.TWEET_COPY.getId())
                .actionName("ツイートをコピー")
                .action(e -> {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(e.getTweet().getText());
                    clipboard.setContent(content);
                })
                .build());
    }
}
