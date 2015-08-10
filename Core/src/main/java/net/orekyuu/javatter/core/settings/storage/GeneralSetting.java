package net.orekyuu.javatter.core.settings.storage;

public final class GeneralSetting {

    private boolean checkRT;
    private boolean checkFavorite;
    private boolean checkTweet;

    public boolean isCheckRT() {
        return checkRT;
    }

    public void setCheckRT(boolean checkRT) {
        this.checkRT = checkRT;
    }

    public boolean isCheckFavorite() {
        return checkFavorite;
    }

    public void setCheckFavorite(boolean checkFavorite) {
        this.checkFavorite = checkFavorite;
    }

    public boolean isCheckTweet() {
        return checkTweet;
    }

    public void setCheckTweet(boolean checkTweet) {
        this.checkTweet = checkTweet;
    }
}
