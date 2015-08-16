package net.orekyuu.javatter.core.settings.storage;

import net.orekyuu.javatter.api.twitter.model.User;

public enum NameViewType {
    SN("ScreenName") {
        @Override
        public String convert(User user) {
            return "@" + user.getScreenName();
        }
    },
    NAME("Name") {
        @Override
        public String convert(User user) {
            return user.getName();
        }
    },
    SN_NAME("ScreenName / Name") {
        @Override
        public String convert(User user) {
            return "@" + user.getScreenName() + " / " + user.getName();
        }
    },
    NAME_SN("Name / ScreenName") {
        @Override
        public String convert(User user) {
            return user.getName() + " / @" + user.getScreenName();
        }
    };

    private final String view;

    NameViewType(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public abstract String convert(User user);
}
