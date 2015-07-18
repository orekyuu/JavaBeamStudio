package net.orekyuu.javatter.core.command;

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
        for (Command command : manager.getCommands()) {
            System.out.println("/" + command.command() + "  " + command.help());
        }
    }
}
