package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductRepository;
import pl.com.bottega.inventory.domain.commands.CollectPurchaseCommand;
import pl.com.bottega.inventory.domain.commands.Command;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.Validatable;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Component
public class CollectPurchaseHandler implements Handler<CollectPurchaseCommand, ProductsSummaryDto> {

    private ProductRepository productRepository;

    public CollectPurchaseHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductsSummaryDto handle(CollectPurchaseCommand command) {

        Validatable.ValidationErrors validationErrors = new Validatable.ValidationErrors();

        ProductsSummaryDto productsSummary;
        Map<String, Integer> productsPurchased = command.getProducts();
        Map<String, Integer> productsToSave = new HashMap<>();
        Map<String, Integer> productsSuccess = new HashMap<>();
        Map<String, Integer> productsFailed = new HashMap<>();

        for (String key : productsPurchased.keySet()) {
            Product product = productRepository.getByScucode(key);
            if (!(product == null) && (product.getCount() > productsPurchased.get(key) || product.getCount() == productsPurchased.get(key))) {
                productsSuccess.put(key, productsPurchased.get(key));
                productsToSave.put(key, product.getCount() - productsPurchased.get(key));

            } else {
                productsFailed.put(key, productsPurchased.get(key));
                if (product == null)
                    validationErrors.add(key.toString(), "no such sku");
            }

        }


        if (productsPurchased.size() == productsSuccess.size()) {
            safeProductsToRepo(productsToSave);
            productsSummary = new ProductsSummarySuccessDto(productsSuccess);
        } else {
            productsSummary = new ProductSummaryFailedDto(productsFailed);
            if (!(validationErrors.isValid()))
                throw new InvalidCommandException(validationErrors);

        }

        return productsSummary;
    }

    private void safeProductsToRepo(Map<String, Integer> productsToSave) {
        productsToSave.keySet().forEach(key -> {
            Product product = productRepository.getByScucode(key);
            product.setCount(productsToSave.get(key));
            productRepository.save(product);
        });
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return CollectPurchaseCommand.class;
    }
}
