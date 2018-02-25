package pl.com.bottega.inventory.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @Column(name = "scucode")
    private String scuCode;
    private Integer amount = 0;

    public Product(){}

    public Product(String scuCode, int amount) {
        this.scuCode = scuCode;
        this.amount = amount;
    }


    public String getScuCode() {
        return scuCode;
    }

    public void setScuCode(String scuCode) {
        this.scuCode = scuCode;
    }

    public int getCount() {
        return amount;
    }

    public void setCount(int amount) {
        this.amount = amount;
    }
}
