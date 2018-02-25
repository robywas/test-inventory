package pl.com.bottega.inventory.infrastructure;


import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class HsqlProductRepository implements ProductRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    public HsqlProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Product getByScucode(String scucode) {
        Product product = entityManager.find(Product.class, scucode);
//        if (product == null)
//                throw new NoSuchEntityException();
            return product;
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public int isPresent(String scucode) {

        return entityManager.createQuery("select count(p) from Product p " +
                "where p.scucode like :scucode")
                .setParameter("scucode", scucode)
                .getFirstResult();
    }
}
