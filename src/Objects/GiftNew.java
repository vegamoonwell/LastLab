package Objects;


import javax.persistence.*;
import java.util.List;

@Entity(name = "gift")
public class GiftNew {
    @Id
    @GeneratedValue
    Long id;
    @Column(nullable = false)
    String boxForm;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "gift_id")
    private List<SweetsNew> sweets;

    public GiftNew(String boxForm) {
        setBoxForm(boxForm);
    }

    public GiftNew() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoxForm() {
        return boxForm;
    }

    public void setBoxForm(String boxForm) {
        this.boxForm = boxForm;
    }

    public List<SweetsNew> getSweets() {
        return sweets;
    }

    public void setSweets(List<SweetsNew> sweets) {
        this.sweets = sweets;
    }

    @Override
    public String toString() {
        int price = 0;
        if (sweets != null) {
            for (int i = 0; i < sweets.size(); i++) {
                price += sweets.get(i).getPrice();
            }
        }
        return boxForm + " | price: " + price;
    }
}
