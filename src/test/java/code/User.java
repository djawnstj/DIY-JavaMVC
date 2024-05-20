package code;

public class User {

    private final long id;
    private final Pay pay;

    public User(long id, Pay pay) {
        this.id = id;
        this.pay = pay;
    }

    public long getId() {
        return id;
    }

    public Pay getPay() {
        return pay;
    }
}
