package net.orekyuu.example;

import net.orekyuu.javatter.api.plugin.OnPostInit;
import net.orekyuu.javatter.api.service.CurrentTweetAreaService;

import javax.inject.Inject;

public class ExamplePlugin {

    @Inject
    private CurrentTweetAreaService tweetAreaService;

    @OnPostInit
    public void initialize() {
        tweetAreaService.addChangeTextListener((oldValue, newValue) -> {
            if (newValue.equals("/sample")) {
                tweetAreaService.setText("Hello Javatter Plugin!!!");
            }
        });
    }
}
