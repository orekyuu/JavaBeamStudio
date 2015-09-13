package net.orekyuu.javatter.core.controller;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.orekyuu.javatter.api.account.AccountStorageService;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.controller.OwnerStage;
import net.orekyuu.javatter.core.account.TwitterAccountImpl;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignupController implements Initializable {
    public Pane veil;
    public ProgressIndicator indicator;
    public TextField pinField;
    public WebView webView;

    private ExecutorService executorService = Executors.newSingleThreadExecutor(r -> new Thread(r, "Signup-Task-Thread"));
    private Twitter twitter;
    private RequestToken token;

    @Inject
    private AccountStorageService accountStorageService;
    @OwnerStage
    private Stage owner;
    public Optional<TwitterAccount> account = Optional.empty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        executorService.submit(() -> {
            twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer("rMvLmU5qMgbZwg92Is5g", "RD28Uuu44KeMOs90UuqXAAoVTWXRTmD4H8xYKZSgBk");
            try {
                token = twitter.getOAuthRequestToken();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                webView.getEngine().load(token.getAuthorizationURL());
                veil.setVisible(false);
                indicator.setVisible(false);
            });
        });

        owner.setOnCloseRequest(e -> onClose());
    }

    public void signup() {
        try {
            AccessToken accessToken = twitter.getOAuthAccessToken(token, pinField.getText());
            TwitterAccountImpl account = new TwitterAccountImpl(accessToken);
            this.account = Optional.of(account);
            accountStorageService.save(account);
            executorService.shutdown();
            owner.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("アカウントの変更を適用するにはアプリを再起動してください。");
            alert.showAndWait();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void onClose() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    public Optional<TwitterAccount> getAccount() {
        return account;
    }
}
