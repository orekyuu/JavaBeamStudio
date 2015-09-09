package net.orekyuu.example;

import net.orekyuu.javatter.api.command.Command;
import net.orekyuu.javatter.api.service.MainTweetAreaService;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import java.util.List;

public class MyCommand implements Command {
    @Override
    public String command() {
        return "hello";
    }

    @Override
    public String help() {
        return "コマンドのサンプルです";
    }

    @Override
    public void exec(List<String> args) {
        MainTweetAreaService service = Lookup.lookup(MainTweetAreaService.class);
        service.setText("Hello Javatter Plugin!");
    }
}
