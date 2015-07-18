package net.orekyuu.javatter.core.command;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.command.Command;
import net.orekyuu.javatter.api.command.CommandManager;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandManagerImpl implements CommandManager {

    private MutableList<Command> commands = Lists.mutable.empty();

    public CommandManagerImpl() {
        registerCommand(new HelpCommand());
        registerCommand(new VersionCommand());
        registerCommand(new SignupCommand());
        registerCommand(new JavaBeamCommand());
    }

    public MutableList<Command> getCommands() {
        return commands;
    }

    @Override
    public void registerCommand(Command command) {
        if (commands.anySatisfy(c -> Objects.equals(c.command(), command.command()))) {
            throw new IllegalArgumentException(command.command() + " is already registered.");
        }
        commands.add(command);
    }

    private Optional<Command> findByName(String name) {
        for (Command command : commands) {
            if (command.command().equals(name)) {
                return Optional.of(command);
            }
        }
        return Optional.empty();
    }

    @Override
    public MutableList<Runnable> findCommand(String text) {
        return parse(text).task;
    }

    @Override
    public String execCommand(String text) {
        CommandParseResult parse = parse(text);
        parse.task.each(Runnable::run);
        return parse.text;
    }

    public CommandParseResult parse(String text) {
        String[] tokens = text.split(" ");
        MutableList<String> args = Lists.mutable.empty();
        MutableList<Runnable> runnables = Lists.mutable.empty();
        String resultText = "";

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            //スラッシュから始まればコマンドとして認識
            if (token.startsWith("/")) {
                token = token.substring(1);
                ArrayList<String> strings = new ArrayList<>(args);
                findByName(token).ifPresent(command -> runnables.add(0, command.runner(strings)));
                args.clear();
            } else {
                //先頭からにトークンを入れていく
                args.add(0, token);
            }
        }

        CommandParseResult result = new CommandParseResult();
        result.task = runnables;
        //コマンドと関係無いものを戻す
        result.text = args.stream().collect(Collectors.joining(" "));
        return result;
    }

    static class CommandParseResult {
        String text;
        MutableList<Runnable> task;
    }
}
