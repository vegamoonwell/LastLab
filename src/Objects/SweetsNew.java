package Objects;

import javax.persistence.*;

@Entity(name = "sweets")
public class SweetsNew {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private String type;
    @ManyToOne
    @JoinColumn(name = "gift_id", referencedColumnName = "id")
    private GiftNew gift;

    public SweetsNew() {
    }

    public SweetsNew(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GiftNew getGift() {
        return gift;
    }

    public void setGift(GiftNew gift) {
        this.gift = gift;
    }

    @Override
    public String toString() {
        return type + " : " + getName() + " : "+ price;
    }
}
