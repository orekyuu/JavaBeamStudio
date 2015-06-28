package net.orekyuu.javatter.api.util.lookup;

public interface Lookuper {

    <T> T lookup(Class<T> clazz);

    void inject(Object object);
}
