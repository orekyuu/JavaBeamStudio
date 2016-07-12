package net.orekyuu.javatter.api.util.lookup;

public final class LazyLookup<T> {

    private Class<T> clazz;
    private T instance;

    public LazyLookup(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T get() {
        if (instance == null) {
            instance = Lookup.lookup(clazz);
            clazz = null;
        }
        return instance;
    }
}
