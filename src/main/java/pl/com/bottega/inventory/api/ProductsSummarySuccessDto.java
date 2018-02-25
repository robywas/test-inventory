package pl.com.bottega.inventory.api;

import java.util.HashMap;
import java.util.Map;

public class ProductsSummarySuccessDto extends ProductsSummaryDto {


    private boolean success = true;
    private Map<String, Integer> purchasedProducts = new HashMap<>();

    public ProductsSummarySuccessDto(Map<String, Integer> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public ProductsSummarySuccessDto(){

    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void add(String skuCode, Integer amount){
        this.purchasedProducts.put(skuCode,amount);
    }
}
