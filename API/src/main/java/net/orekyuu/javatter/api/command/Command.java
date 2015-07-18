package net.orekyuu.javatter.api.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Command {

    String command();

    String help();

    void exec(List<String> args);

    default Runnable runner(String... args) {
        return runner(Arrays.asList(args));
    }

    default Runnable runner(List<String> args) {
        return () -> exec(new ArrayList<>(args));
    }
}
