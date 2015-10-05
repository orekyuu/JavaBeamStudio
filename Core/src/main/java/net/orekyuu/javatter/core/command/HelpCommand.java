package net.orekyuu.javatter.core.command;

import com.gs.collections.api.list.MutableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.orekyuu.javatter.api.command.Command;
import net.orekyuu.javatter.api.command.CommandManager;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import java.util.List;

public class HelpCommand implements Command {
    @Override
    public String command() {
        return "help";
    }

    @Override
    public String help() {
        return "コマンドの説明を表示します。";
    }

    @Override
    public void exec(List<String> args) {
        CommandManagerImpl manager = (CommandManagerImpl) Lookup.lookup(CommandManager.class);
        MutableList<CommandValue> values = manager.getCommands()
                .collect(command -> new CommandValue(command.command(), command.help()));

        TableView<CommandValue> table = new TableView<>();
        TableColumn commandColumn = new TableColumn("コマンド");
        TableColumn descColumn = new TableColumn("説明");

        table.getColumns().setAll(commandColumn, descColumn);
        table.setItems(FXCollections.observableArrayList(values));

        commandColumn.setCellValueFactory(new PropertyValueFactory<CommandValue, String>("command"));
        descColumn.setCellValueFactory(new PropertyValueFactory<CommandValue, String>("desc"));

        Stage stage = new Stage();
        stage.setScene(new Scene(table, 400, 400));
        stage.centerOnScreen();
        stage.show();
    }

    public class CommandValue {
        private final SimpleStringProperty command;
        private final SimpleStringProperty desc;

        public CommandValue(String command, String desc) {
            this.command = new SimpleStringProperty(command);
            this.desc = new SimpleStringProperty(desc);
        }

        public String getCommand() {
            return command.get();
        }

        public SimpleStringProperty commandProperty() {
            return command;
        }

        public void setCommand(String command) {
            this.command.set(command);
        }

        public String getDesc() {
            return desc.get();
        }

        public SimpleStringProperty descProperty() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc.set(desc);
        }
    }
}
