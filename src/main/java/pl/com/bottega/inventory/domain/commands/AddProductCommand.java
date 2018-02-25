package pl.com.bottega.inventory.domain.commands;

public class AddProductCommand implements Command{


    private String skuCode;
    private Integer amount;

    public String getSkuCode() {
        return skuCode;
    }

    public int getCount() {
        return amount;
    }

    public void validate(Validatable.ValidationErrors errors) {
        validatePresenceOf("skuCode", skuCode, errors);
        if (skuCode == "" || skuCode == null)
            errors.add("skuCode","can't be blank" );

        if (amount == 0)
            errors.add("amount","can't be blank"  );

        if ((amount > 999 || amount < 1) && amount != 0)
            errors.add("amount", "must be between 1 and 999");
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
