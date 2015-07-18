package net.orekyuu.javatter.api.command;

import com.gs.collections.api.list.MutableList;

public interface CommandManager {

    void registerCommand(Command command);

    MutableList<Runnable> findCommand(String text);

    String execCommand(String text);
}
