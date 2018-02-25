package pl.com.bottega.inventory.domain.commands;

import java.util.HashMap;
import java.util.Map;

public class CollectPurchaseCommand implements Command {

    private Map<String, Integer> products;

    public Map<String, Integer> getProducts() {
        return products;
    }

    public CollectPurchaseCommand(HashMap<String, Integer> products) {
        this.products = products;
    }

    public void validate(Validatable.ValidationErrors errors) {

        if(products.size()==0)
            errors.add("skus","are required");

        for (String key : products.keySet()){
            if (products.get(key) > 999 || products.get(key) < 1 )
                errors.add(key.toString(),"must be between 1 and 999");
        }
    }



}
