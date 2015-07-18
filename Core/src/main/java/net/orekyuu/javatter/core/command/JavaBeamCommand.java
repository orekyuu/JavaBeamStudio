package net.orekyuu.javatter.core.command;

import net.orekyuu.javatter.api.command.Command;
import net.orekyuu.javatter.api.service.CurrentTweetAreaService;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import java.util.List;
import java.util.Random;

public class JavaBeamCommand implements Command {

    private final Random rand = new Random();

    @Override
    public String command() {
        return "javabeam";
    }

    @Override
    public String help() {
        return "Javaビームを生成します。";
    }

    @Override
    public void exec(List<String> args) {
        CurrentTweetAreaService service = Lookup.lookup(CurrentTweetAreaService.class);
        StringBuilder builder = new StringBuilder("Javaビーム");


        for (int i = 0; i < rand.nextInt(5) + 3; i++) {
            builder.append("ﾋﾞ");
        }

        for (int i = 0; i < rand.nextInt(5) + 5; i++) {
            builder.append("w");
        }
        service.setText(builder.toString());
    }
}
