package pl.com.bottega.inventory.ui;


import org.springframework.web.bind.annotation.*;
import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.api.ProductsSummaryDto;
import pl.com.bottega.inventory.domain.commands.AddProductCommand;
import pl.com.bottega.inventory.domain.commands.CollectPurchaseCommand;

import java.util.HashMap;

@RestController
public class PurchaseController {


    CommandGateway commandGateway;

    public PurchaseController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/inventory")
    public void addProduct(@RequestBody AddProductCommand addProductCommand) {

        commandGateway.execute(addProductCommand);

    }

    @PostMapping("/purchase")
    public ProductsSummaryDto makePurchase(@RequestBody HashMap<String, Integer> products){
        CollectPurchaseCommand command = new CollectPurchaseCommand(products);

        ProductsSummaryDto productSummary= commandGateway.execute(command);

        return productSummary;

    }



}