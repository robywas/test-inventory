package pl.com.bottega.inventory.domain;



public interface ProductRepository {

    Product getByScucode(String scucode);

    void save(Product product);

    int isPresent(String scucode);
}

