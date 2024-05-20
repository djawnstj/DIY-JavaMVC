package code;

public class Item {

    private long id;
    private long price;
    private String description;

    public Item(long id, long price, String description) {
        this.id = id;
        this.price = price;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
