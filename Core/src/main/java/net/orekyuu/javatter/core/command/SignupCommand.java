package net.orekyuu.javatter.core.command;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.orekyuu.javatter.api.command.Command;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.core.controller.SignupController;

import java.io.IOException;
import java.util.List;

public class SignupCommand implements Command {
    @Override
    public String command() {
        return "signup";
    }

    @Override
    public String help() {
        return "新しいユーザーを認証します";
    }

    @Override
    public void exec(List<String> args) {
        JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource("/layout/signup.fxml"));
        Stage stage = new Stage();
        loader.setOwnerStage(stage);
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.requestFocus();
            stage.show();
            SignupController controller = loader.getController();
            stage.setOnCloseRequest(e -> controller.onClose());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
