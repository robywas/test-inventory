package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductRepository;
import pl.com.bottega.inventory.domain.commands.AddProductCommand;
import pl.com.bottega.inventory.domain.commands.Command;

import javax.transaction.Transactional;

@Component
public class AddProductHandler implements Handler<AddProductCommand,Void> {

    ProductRepository productRepository;

    public AddProductHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public Void handle(AddProductCommand command) {

        Product product = productRepository.getByScucode(command.getSkuCode());
        if (!(product == null))
            product.setCount(product.getCount() + command.getCount());
        else
            product = new Product(command.getSkuCode(), command.getCount());

        productRepository.save(product);

        return null;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddProductCommand.class;
    }


    private boolean isPresent(AddProductCommand command) {
        Product product = productRepository.getByScucode(command.getSkuCode());
        return product != null;
    }


}
