package net.orekyuu.javatter.core.controller;

import com.gs.collections.api.list.ImmutableList;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.orekyuu.javatter.api.account.AccountStorageService;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountSettingsController implements Initializable {
    public ListView<TwitterAccount> accounts;
    public Button remove;
    public Button add;

    @Inject
    private AccountStorageService accountStorage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accounts.setCellFactory(new Callback<ListView<TwitterAccount>, ListCell<TwitterAccount>>() {
            @Override
            public ListCell<TwitterAccount> call(ListView<TwitterAccount> param) {
                return new ListCell<TwitterAccount>() {
                    @Override
                    protected void updateItem(TwitterAccount item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                            return;
                        }
                        setText("@" + item.getId());
                    }
                };
            }
        });

        updateAccountsList();
    }

    public void removeAccount() {
        MultipleSelectionModel<TwitterAccount> selectionModel = accounts.getSelectionModel();
        ObservableList<TwitterAccount> selectedItems = selectionModel.getSelectedItems();
        accountStorage.delete(selectedItems);
        updateAccountsList();
    }

    public void addAccount() {
        JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource("/layout/signup.fxml"));
        Stage stage = new Stage();
        loader.setOwnerStage(stage);
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.requestFocus();
            stage.showAndWait();
            SignupController controller = loader.getController();
            controller.onClose();
            Optional<TwitterAccount> account = controller.getAccount();
            account.ifPresent(a -> updateAccountsList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAccountsList() {
        ImmutableList<TwitterAccount> accountList = accountStorage.findByType(TwitterAccount.class);
        accounts.getItems().setAll(accountList.toList());
    }
}
