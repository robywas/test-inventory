package pl.com.bottega.inventory.api;

public interface Repository<T> {

    void save (T t);

}
