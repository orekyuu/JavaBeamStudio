package net.orekyuu.javatter.api.util.lookup;

public class Lookup {

    private static Lookuper lookuper;

    public static void setLookuper(Lookuper lookuper) {
        //この実装が気に食わない。あとで何とかしたい。
        //というかこのパッケージの存在自体がちょっとアレ。せめて名前かえるべき？
        if (Lookup.lookuper != null) {
            throw new IllegalStateException("lookuper is initialized");
        }
        Lookup.lookuper = lookuper;
    }

    public static <T> T lookup(Class<T> t) {
        return lookuper.lookup(t);
    }

    public static void inject(Object inject) {
        lookuper.inject(inject);
    }
}
