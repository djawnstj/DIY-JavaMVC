package study;

class Product {
    private long id;
    private long price;
    private String description;

    Product(long id, long price, String description) {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}