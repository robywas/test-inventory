package pl.com.bottega.inventory.api;

import java.util.Map;

public class ProductSummaryFailedDto extends ProductsSummaryDto {

    private boolean success;
    private Map<String, Integer> missingProducts;

    public ProductSummaryFailedDto(Map<String, Integer> missingProducts) {
        this.success = false;
        this.missingProducts = missingProducts;
    }

    public ProductSummaryFailedDto() {
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, Integer> getMissingProducts() {
        return missingProducts;
    }
}
